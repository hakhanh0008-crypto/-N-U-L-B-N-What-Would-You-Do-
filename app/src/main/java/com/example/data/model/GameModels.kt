package com.example.data.model

enum class GameMode(
    val title: String,
    val subtitle: String,
    val iconEmoji: String,
    val description: String
) {
    DAILY(
        title = "Chuyện thường ngày",
        subtitle = "Tình bạn & Giao tiếp",
        iconEmoji = "🌸",
        description = "Tình bạn, hiểu lầm ngẫu nhiên, mượn đồ, giữ lời hứa và ranh giới cá nhân."
    ),
    TEAMWORK(
        title = "Sinh tồn nhóm",
        subtitle = "Đại học & Deadline",
        iconEmoji = "🎓",
        description = "Chia việc không đều, thành viên 'biến mất', áp lực deadline và cách dàn xếp công bằng."
    ),
    TWIST(
        title = "Cú twist bất ngờ",
        subtitle = "Nghịch lý đạo đức & Tình thế éo le",
        iconEmoji = "⚡",
        description = "Những tình huống ngã ba đường không có đáp án đúng/sai rõ ràng, buộc bạn phải cân nhắc giữa lòng trung thành, đạo đức và sự an toàn trước các hậu quả khôn lường."
    ),
    CUSTOM(
        title = "Tự tạo tình huống",
        subtitle = "AI Tự Do Tạo Đề",
        iconEmoji = "✨",
        description = "Nhập bất kỳ tình huống nào bạn từng gặp để AI thiết kế thử thách nhập vai độc nhất!"
    )
}

data class CharacterProfile(
    val name: String,
    val role: String,
    val avatarEmoji: String,
    val trait: String
)

data class ChoiceImpact(
    val communication: Int = 0,
    val empathy: Int = 0,
    val riskManagement: Int = 0,
    val boundarySetting: Int = 0
)

data class ScenarioChoice(
    val id: String,
    val label: String, // "A", "B", "C", "D"
    val title: String,
    val description: String,
    val impact: ChoiceImpact
)

data class GameScenario(
    val id: String,
    val roundNumber: Int,
    val totalRounds: Int = 5,
    val mode: GameMode,
    val title: String,
    val contextSetting: String,
    val characters: List<CharacterProfile>,
    val situation: String,
    val choices: List<ScenarioChoice>,
    val isLiveAi: Boolean = false
)

data class AiFeedback(
    val choiceId: String,
    val choiceLabel: String,
    val choiceSummary: String,
    val strengths: String,
    val drawbacks: String,
    val shortTermConsequence: String,
    val longTermConsequence: String,
    val alternativePerspective: String,
    val statImpact: ChoiceImpact,
    val isLiveAi: Boolean = false
)

data class PlayerStats(
    val communication: Int = 50,
    val empathy: Int = 50,
    val riskManagement: Int = 50,
    val boundarySetting: Int = 50
) {
    fun applyImpact(impact: ChoiceImpact): PlayerStats {
        return PlayerStats(
            communication = (communication + impact.communication).coerceIn(10, 100),
            empathy = (empathy + impact.empathy).coerceIn(10, 100),
            riskManagement = (riskManagement + impact.riskManagement).coerceIn(10, 100),
            boundarySetting = (boundarySetting + impact.boundarySetting).coerceIn(10, 100)
        )
    }
}

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val category: String = "Tổng quan",
    val iconEmoji: String,
    val isUnlocked: Boolean = false,
    val unlockHint: String = ""
)

data class EndGameReport(
    val archetypeTitle: String,
    val archetypeSubtitle: String,
    val archetypeDescription: String,
    val strengths: List<String>,
    val blindSpots: List<String>,
    val practicalAdvice: List<String>,
    val finalStats: PlayerStats,
    val secretEndingUnlocked: Boolean,
    val secretEndingTitle: String? = null,
    val secretEndingStory: String? = null,
    val isLiveAi: Boolean = false
)

data class DecisionHistoryItem(
    val scenarioTitle: String,
    val choiceLabel: String,
    val choiceTitle: String,
    val keyTakeaway: String
)
