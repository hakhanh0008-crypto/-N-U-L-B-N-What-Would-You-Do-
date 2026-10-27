package com.example.data.ai

import android.util.Log
import com.example.BuildConfig
import com.example.data.model.*
import com.example.data.offline.OfflineScenarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiApiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(25, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    companion object {
        private const val TAG = "GeminiApiService"
        private const val API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"
    }

    private fun isApiKeyConfigured(): Boolean {
        val key = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            null
        }
        return !key.isNullOrBlank() && key != "MY_GEMINI_API_KEY"
    }

    suspend fun generateScenario(
        mode: GameMode,
        roundNumber: Int,
        previousChoices: List<DecisionHistoryItem>,
        customTopic: String? = null
    ): GameScenario = withContext(Dispatchers.IO) {
        if (!isApiKeyConfigured()) {
            val list = OfflineScenarioRepository.getScenariosForMode(mode, customTopic)
            return@withContext list.getOrElse(roundNumber - 1) { list.last() }
        }

        try {
            val prompt = buildScenarioPrompt(mode, roundNumber, previousChoices, customTopic)
            val jsonResponse = callGeminiRaw(prompt) ?: throw IllegalStateException("Empty AI response")
            parseScenarioJson(jsonResponse, mode, roundNumber)
        } catch (e: Exception) {
            Log.w(TAG, "Gemini scenario generation fallback due to: ${e.message}")
            val list = OfflineScenarioRepository.getScenariosForMode(mode, customTopic)
            list.getOrElse(roundNumber - 1) { list.last() }
        }
    }

    suspend fun analyzeChoice(
        scenario: GameScenario,
        choice: ScenarioChoice,
        currentStats: PlayerStats
    ): AiFeedback = withContext(Dispatchers.IO) {
        if (!isApiKeyConfigured()) {
            return@withContext OfflineScenarioRepository.getOfflineFeedback(choice, isLiveAi = false)
        }

        try {
            val prompt = buildAnalysisPrompt(scenario, choice, currentStats)
            val jsonResponse = callGeminiRaw(prompt) ?: throw IllegalStateException("Empty AI response")
            parseAnalysisJson(jsonResponse, choice)
        } catch (e: Exception) {
            Log.w(TAG, "Gemini choice analysis fallback due to: ${e.message}")
            OfflineScenarioRepository.getOfflineFeedback(choice, isLiveAi = false)
        }
    }

    suspend fun generateFinalReport(
        stats: PlayerStats,
        history: List<DecisionHistoryItem>
    ): EndGameReport = withContext(Dispatchers.IO) {
        if (!isApiKeyConfigured()) {
            return@withContext OfflineScenarioRepository.generateEndGameReport(stats, history, isLiveAi = false)
        }

        try {
            val prompt = buildReportPrompt(stats, history)
            val jsonResponse = callGeminiRaw(prompt) ?: throw IllegalStateException("Empty AI response")
            parseReportJson(jsonResponse, stats)
        } catch (e: Exception) {
            Log.w(TAG, "Gemini report generation fallback due to: ${e.message}")
            OfflineScenarioRepository.generateEndGameReport(stats, history, isLiveAi = false)
        }
    }

    private fun callGeminiRaw(userPrompt: String): String? {
        val apiKey = BuildConfig.GEMINI_API_KEY
        val requestUrl = "$API_URL?key=$apiKey"

        val rootJson = JSONObject().apply {
            val contents = JSONArray().apply {
                val contentObj = JSONObject().apply {
                    val parts = JSONArray().apply {
                        put(JSONObject().apply { put("text", userPrompt) })
                    }
                    put("parts", parts)
                }
                put(contentObj)
            }
            put("contents", contents)

            val config = JSONObject().apply {
                put("responseMimeType", "application/json")
                put("temperature", 0.7)
            }
            put("generationConfig", config)
        }

        val body = rootJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url(requestUrl)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e(TAG, "Gemini API HTTP ${response.code}: ${response.message}")
                return null
            }
            val respBody = response.body?.string() ?: return null
            val respJson = JSONObject(respBody)
            val candidates = respJson.optJSONArray("candidates") ?: return null
            if (candidates.length() == 0) return null
            val firstCandidate = candidates.getJSONObject(0)
            val content = firstCandidate.optJSONObject("content") ?: return null
            val parts = content.optJSONArray("parts") ?: return null
            if (parts.length() == 0) return null
            return parts.getJSONObject(0).optString("text")
        }
    }

    private fun buildScenarioPrompt(
        mode: GameMode,
        roundNumber: Int,
        previousChoices: List<DecisionHistoryItem>,
        customTopic: String?
    ): String {
        val historyContext = if (previousChoices.isEmpty()) {
            "Chưa có quyết định nào trước đó."
        } else {
            previousChoices.joinToString("; ") { "Tình huống: ${it.scenarioTitle} -> Bạn chọn: ${it.choiceTitle}" }
        }

        return """
            Bạn là biên kịch game tương tác xã hội "NẾU LÀ BẠN? — What Would You Do?".
            Hãy tạo tình huống thứ $roundNumber / 5 cho chế độ: ${mode.title}.
            Chủ đề tùy chọn nếu có: ${customTopic ?: "không có"}.
            Dữ liệu các quyết định trước đó của người chơi: $historyContext.
            Hãy thích ứng diễn biến tình huống này dựa trên các lựa chọn trước đó (ví dụ nếu người chơi từng thỏa hiệp, lần này áp lực sẽ khác; nếu từng đối đầu, sẽ có hậu quả).

            Yêu cầu:
            1. Tình huống phải chân thực, ngôn ngữ tiếng Việt tự nhiên của sinh viên / người trẻ.
            2. KHÔNG có lựa chọn nào hoàn hảo tuyệt đối (mỗi lựa chọn đều có điểm mạnh và cái giá phải trả).
            ${if (mode == GameMode.TWIST) "3. ĐẶC BIỆT CHẾ ĐỘ 'CÚ TWIST BẤT NGỜ': Tạo ra tình huống éo le, ngã ba đường đạo đức bất ngờ không có đáp án đúng/sai tuyệt đối. Buộc người chơi phải giằng xé giữa các giá trị đối lập (ví dụ: trung thành vs liêm chính, bảo vệ bạn bè vs tuân thủ pháp luật, cơ hội sự nghiệp vs tình cảm gia đình), đòi hỏi tư duy chiến lược và đạo đức sâu sắc." else ""}
            4. Tạo 3 hoặc 4 lựa chọn (A, B, C, D) với các phong cách: Giao tiếp, Thấu cảm, Quản trị rủi ro, Thiết lập ranh giới.
            
            Trả về duy nhất JSON hợp lệ theo schema sau:
            {
              "title": "Tên tình huống ngắn gọn",
              "contextSetting": "Địa điểm & bối cảnh thời gian",
              "characters": [
                {"name": "Tên nhân vật", "role": "Mối quan hệ", "avatarEmoji": "👤", "trait": "Đặc điểm tính cách"}
              ],
              "situation": "Mô tả tình huống kịch tính, chi tiết 3-4 câu",
              "choices": [
                {
                  "label": "A",
                  "title": "Tên lựa chọn",
                  "description": "Hành động cụ thể của người chơi",
                  "impact": {
                    "communication": 15,
                    "empathy": 10,
                    "riskManagement": 20,
                    "boundarySetting": -5
                  }
                }
              ]
            }
        """.trimIndent()
    }

    private fun buildAnalysisPrompt(
        scenario: GameScenario,
        choice: ScenarioChoice,
        currentStats: PlayerStats
    ): String {
        return """
            Bạn là chuyên gia phân tích tâm lý và kỹ năng xã hội trong game "NẾU LÀ BẠN?".
            Phân tích lựa chọn của người chơi một cách trung lập, tinh tế, không phán xét đúng/sai.
            
            Tình huống: "${scenario.title}" - ${scenario.situation}
            Lựa chọn của người chơi: [${choice.label}] ${choice.title}: ${choice.description}
            Chỉ số hiện tại: Giao tiếp: ${currentStats.communication}, Thấu cảm: ${currentStats.empathy}, Rủi ro: ${currentStats.riskManagement}, Ranh giới: ${currentStats.boundarySetting}.
            
            Trả về JSON hợp lệ theo schema:
            {
              "strengths": "Điểm sáng, lợi thế và phẩm chất tích cực từ hành động này",
              "drawbacks": "Cái giá phải trả, hạn chế hoặc rủi ro tiềm ẩn",
              "shortTermConsequence": "Diễn biến ngắn hạn trong 24-48 giờ tới",
              "longTermConsequence": "Tác động dài hạn đến uy tín và mối quan hệ",
              "alternativePerspective": "Góc nhìn thấu cảm từ phía đối phương trong tình huống"
            }
        """.trimIndent()
    }

    private fun buildReportPrompt(
        stats: PlayerStats,
        history: List<DecisionHistoryItem>
    ): String {
        val historyStr = history.joinToString("\n") { "- ${it.scenarioTitle}: Chọn ${it.choiceLabel} (${it.choiceTitle})" }
        return """
            Bạn là chuyên gia tổng kết hành trình phản xạ xã hội của game "NẾU LÀ BẠN?".
            Chỉ số chung cuộc:
            - Giao tiếp: ${stats.communication} / 100
            - Thấu cảm: ${stats.empathy} / 100
            - Quản trị rủi ro: ${stats.riskManagement} / 100
            - Thiết lập ranh giới: ${stats.boundarySetting} / 100
            
            Lịch sử 5 quyết định:
            $historyStr
            
            Hãy đưa ra phân tích phong cách quyết định chân thành, ấm áp, có sắc thái tinh tế (không dùng từ ngữ y khoa hay kết luận bệnh lý).
            Trả về JSON theo schema:
            {
              "archetypeTitle": "Danh hiệu phong cách (ví dụ: Nhà Ngoại Giao Quyết Đoán, Người Giữ Lửa Hòa Bình...)",
              "archetypeSubtitle": "Mô tả ngắn gọn 3-5 từ",
              "archetypeDescription": "Đoạn văn nhận định tổng thể phong cách hành xử (3-4 câu)",
              "strengths": ["Điểm mạnh 1", "Điểm mạnh 2", "Điểm mạnh 3"],
              "blindSpots": ["Vùng mù nhận thức 1", "Vùng mù nhận thức 2"],
              "practicalAdvice": ["Lời khuyên thực tiễn 1", "Lời khuyên thực tiễn 2", "Lời khuyên thực tiễn 3"]
            }
        """.trimIndent()
    }

    private fun parseScenarioJson(jsonStr: String, mode: GameMode, round: Int): GameScenario {
        val json = JSONObject(jsonStr)
        val title = json.getString("title")
        val contextSetting = json.optString("contextSetting", "Bối cảnh sinh viên")
        val situation = json.getString("situation")

        val charList = mutableListOf<CharacterProfile>()
        val charsArr = json.optJSONArray("characters")
        if (charsArr != null) {
            for (i in 0 until charsArr.length()) {
                val c = charsArr.getJSONObject(i)
                charList.add(
                    CharacterProfile(
                        name = c.optString("name", "Bạn bè"),
                        role = c.optString("role", "Thành viên"),
                        avatarEmoji = c.optString("avatarEmoji", "👤"),
                        trait = c.optString("trait", "Tính cách")
                    )
                )
            }
        }
        if (charList.isEmpty()) {
            charList.add(CharacterProfile("Nhân vật", "Đối tác", "👥", "Thân thiện"))
        }

        val choiceList = mutableListOf<ScenarioChoice>()
        val choicesArr = json.getJSONArray("choices")
        for (i in 0 until choicesArr.length()) {
            val ch = choicesArr.getJSONObject(i)
            val impactObj = ch.optJSONObject("impact")
            val impact = if (impactObj != null) {
                ChoiceImpact(
                    communication = impactObj.optInt("communication", 10),
                    empathy = impactObj.optInt("empathy", 10),
                    riskManagement = impactObj.optInt("riskManagement", 10),
                    boundarySetting = impactObj.optInt("boundarySetting", 10)
                )
            } else {
                ChoiceImpact(10, 10, 10, 10)
            }
            choiceList.add(
                ScenarioChoice(
                    id = "live_c_$i",
                    label = ch.optString("label", ('A' + i).toString()),
                    title = ch.getString("title"),
                    description = ch.getString("description"),
                    impact = impact
                )
            )
        }

        return GameScenario(
            id = "live_scenario_$round",
            roundNumber = round,
            totalRounds = 5,
            mode = mode,
            title = title,
            contextSetting = contextSetting,
            characters = charList,
            situation = situation,
            choices = choiceList,
            isLiveAi = true
        )
    }

    private fun parseAnalysisJson(jsonStr: String, choice: ScenarioChoice): AiFeedback {
        val json = JSONObject(jsonStr)
        return AiFeedback(
            choiceId = choice.id,
            choiceLabel = choice.label,
            choiceSummary = choice.title,
            strengths = json.optString("strengths", "Lựa chọn cho thấy sự quyết đoán của bạn."),
            drawbacks = json.optString("drawbacks", "Cần chú ý giữ hòa khí lâu dài."),
            shortTermConsequence = json.optString("shortTermConsequence", "Tình hình bước đầu ổn định."),
            longTermConsequence = json.optString("longTermConsequence", "Xây dựng được uy tín vững vàng."),
            alternativePerspective = json.optString("alternativePerspective", "Đối phương sẽ hiểu và tôn trọng bạn."),
            statImpact = choice.impact,
            isLiveAi = true
        )
    }

    private fun parseReportJson(jsonStr: String, stats: PlayerStats): EndGameReport {
        val json = JSONObject(jsonStr)
        val strengths = mutableListOf<String>()
        val sArr = json.optJSONArray("strengths")
        if (sArr != null) {
            for (i in 0 until sArr.length()) strengths.add(sArr.getString(i))
        } else {
            strengths.add("Khả năng ứng biến linh hoạt")
        }

        val blindSpots = mutableListOf<String>()
        val bArr = json.optJSONArray("blindSpots")
        if (bArr != null) {
            for (i in 0 until bArr.length()) blindSpots.add(bArr.getString(i))
        } else {
            blindSpots.add("Đôi khi suy nghĩ quá nhiều trước khi hành động")
        }

        val advice = mutableListOf<String>()
        val aArr = json.optJSONArray("practicalAdvice")
        if (aArr != null) {
            for (i in 0 until aArr.length()) advice.add(aArr.getString(i))
        } else {
            advice.add("Lắng nghe bản thân và kiên định với giá trị cốt lõi")
        }

        val c = stats.communication
        val e = stats.empathy
        val r = stats.riskManagement
        val b = stats.boundarySetting
        val secretUnlocked = (e >= 75 && b >= 75) || (c in 60..85 && e in 60..85 && r in 60..85 && b in 60..85)

        return EndGameReport(
            archetypeTitle = json.optString("archetypeTitle", "Nhà Ngoại Giao Quyết Đoán"),
            archetypeSubtitle = json.optString("archetypeSubtitle", "Khéo léo & Sắc bén"),
            archetypeDescription = json.optString("archetypeDescription", "Phong cách của bạn kết hợp giữa sự chân thành và bảo vệ ranh giới lành mạnh."),
            strengths = strengths,
            blindSpots = blindSpots,
            practicalAdvice = advice,
            finalStats = stats,
            secretEndingUnlocked = secretUnlocked,
            secretEndingTitle = if (secretUnlocked) "Secret Ending: Bậc Thầy Cân Bằng Cảm Xúc" else null,
            secretEndingStory = if (secretUnlocked) "Bạn đã mở khóa kết cục ẩn: Sự cân bằng tuyệt đối giữa thấu cảm và nguyên tắc cá nhân!" else null,
            isLiveAi = true
        )
    }
}
