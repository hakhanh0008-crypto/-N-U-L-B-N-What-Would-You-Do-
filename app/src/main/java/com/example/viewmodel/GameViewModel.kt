package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.GeminiApiService
import com.example.data.local.GamePreferences
import com.example.data.model.*
import com.example.data.offline.OfflineScenarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class ScreenState {
    WELCOME,
    MODE_SELECT,
    CUSTOM_TOPIC,
    SCENARIO,
    FEEDBACK,
    REPORT,
    ACHIEVEMENTS
}

data class GameUiState(
    val currentScreen: ScreenState = ScreenState.WELCOME,
    val selectedMode: GameMode = GameMode.DAILY,
    val customTopic: String = "",
    val currentRound: Int = 1,
    val totalRounds: Int = 5,
    val currentScenario: GameScenario? = null,
    val selectedChoice: ScenarioChoice? = null,
    val currentFeedback: AiFeedback? = null,
    val playerStats: PlayerStats = PlayerStats(),
    val decisionHistory: List<DecisionHistoryItem> = emptyList(),
    val finalReport: EndGameReport? = null,
    val isLoading: Boolean = false,
    val loadingMessage: String = "",
    val isPresentationMode: Boolean = false,
    val showAiExplanation: Boolean = false,
    val showAchievementsDialog: Boolean = false,
    val achievements: List<Achievement> = emptyList(),
    val totalGamesPlayed: Int = 0,
    val errorMessage: String? = null
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = GamePreferences(application)
    private val apiService = GeminiApiService()

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        loadAchievements()
    }

    fun loadAchievements() {
        val unlockedIds = prefs.getUnlockedAchievementIds()
        val all = OfflineScenarioRepository.allAchievements.map { ach ->
            ach.copy(isUnlocked = unlockedIds.contains(ach.id))
        }
        _uiState.update {
            it.copy(
                achievements = all,
                totalGamesPlayed = prefs.getTotalGamesPlayed()
            )
        }
    }

    fun togglePresentationMode() {
        _uiState.update { it.copy(isPresentationMode = !it.isPresentationMode) }
    }

    fun setShowAiExplanation(show: Boolean) {
        _uiState.update { it.copy(showAiExplanation = show) }
    }

    fun setShowAchievementsDialog(show: Boolean) {
        if (show) loadAchievements()
        _uiState.update { it.copy(showAchievementsDialog = show) }
    }

    fun navigateTo(screen: ScreenState) {
        _uiState.update { it.copy(currentScreen = screen, errorMessage = null) }
    }

    fun selectMode(mode: GameMode) {
        _uiState.update { it.copy(selectedMode = mode) }
        if (mode == GameMode.CUSTOM) {
            navigateTo(ScreenState.CUSTOM_TOPIC)
        } else {
            startNewGame(mode, null)
        }
    }

    fun startCustomGame(topic: String) {
        val cleanTopic = topic.trim().ifEmpty { "Hiểu lầm trong đời sống" }
        _uiState.update { it.copy(customTopic = cleanTopic) }
        startNewGame(GameMode.CUSTOM, cleanTopic)
    }

    fun startNewGame(mode: GameMode, topic: String?) {
        _uiState.update {
            it.copy(
                selectedMode = mode,
                customTopic = topic ?: "",
                currentRound = 1,
                playerStats = PlayerStats(50, 50, 50, 50),
                decisionHistory = emptyList(),
                currentScenario = null,
                selectedChoice = null,
                currentFeedback = null,
                finalReport = null,
                currentScreen = ScreenState.SCENARIO,
                errorMessage = null
            )
        }
        loadScenario(1, mode, topic)
    }

    private fun loadScenario(round: Int, mode: GameMode, topic: String?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    loadingMessage = if (it.isPresentationMode) {
                        "Đang chuẩn bị tình huống $round (Chế độ Thuyết trình)..."
                    } else {
                        "✨ AI đang thiết kế kịch bản tình huống $round/5..."
                    }
                )
            }

            try {
                val scenario = if (_uiState.value.isPresentationMode) {
                    val list = OfflineScenarioRepository.getScenariosForMode(mode, topic)
                    list.getOrElse(round - 1) { list.last() }
                } else {
                    apiService.generateScenario(
                        mode = mode,
                        roundNumber = round,
                        previousChoices = _uiState.value.decisionHistory,
                        customTopic = topic
                    )
                }

                _uiState.update {
                    it.copy(
                        currentScenario = scenario,
                        currentRound = round,
                        selectedChoice = null,
                        currentFeedback = null,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                val list = OfflineScenarioRepository.getScenariosForMode(mode, topic)
                val fallbackScenario = list.getOrElse(round - 1) { list.last() }
                _uiState.update {
                    it.copy(
                        currentScenario = fallbackScenario,
                        currentRound = round,
                        selectedChoice = null,
                        currentFeedback = null,
                        isLoading = false,
                        errorMessage = "Đã chuyển sang chế độ dự phòng ngoại tuyến: ${e.message}"
                    )
                }
            }
        }
    }

    fun submitChoice(choice: ScenarioChoice) {
        val scenario = _uiState.value.currentScenario ?: return
        val currentStats = _uiState.value.playerStats

        _uiState.update {
            it.copy(
                selectedChoice = choice,
                isLoading = true,
                loadingMessage = "✨ AI đang phân tích ưu nhược điểm & hậu quả lựa chọn của bạn..."
            )
        }

        viewModelScope.launch {
            try {
                val feedback = if (_uiState.value.isPresentationMode) {
                    OfflineScenarioRepository.getOfflineFeedback(choice, isLiveAi = false)
                } else {
                    apiService.analyzeChoice(scenario, choice, currentStats)
                }

                val newStats = currentStats.applyImpact(choice.impact)
                val historyItem = DecisionHistoryItem(
                    scenarioTitle = scenario.title,
                    choiceLabel = choice.label,
                    choiceTitle = choice.title,
                    keyTakeaway = feedback.strengths
                )

                _uiState.update {
                    it.copy(
                        currentFeedback = feedback,
                        playerStats = newStats,
                        decisionHistory = it.decisionHistory + historyItem,
                        isLoading = false,
                        currentScreen = ScreenState.FEEDBACK
                    )
                }
            } catch (e: Exception) {
                val feedback = OfflineScenarioRepository.getOfflineFeedback(choice, isLiveAi = false)
                val newStats = currentStats.applyImpact(choice.impact)
                val historyItem = DecisionHistoryItem(
                    scenarioTitle = scenario.title,
                    choiceLabel = choice.label,
                    choiceTitle = choice.title,
                    keyTakeaway = feedback.strengths
                )
                _uiState.update {
                    it.copy(
                        currentFeedback = feedback,
                        playerStats = newStats,
                        decisionHistory = it.decisionHistory + historyItem,
                        isLoading = false,
                        currentScreen = ScreenState.FEEDBACK
                    )
                }
            }
        }
    }

    fun proceedToNext() {
        val nextRound = _uiState.value.currentRound + 1
        if (nextRound <= _uiState.value.totalRounds) {
            _uiState.update { it.copy(currentScreen = ScreenState.SCENARIO) }
            loadScenario(nextRound, _uiState.value.selectedMode, _uiState.value.customTopic)
        } else {
            finishGame()
        }
    }

    private fun finishGame() {
        _uiState.update {
            it.copy(
                isLoading = true,
                loadingMessage = "✨ AI đang tổng kết hành trình & phân tích phong cách xã hội của bạn..."
            )
        }

        viewModelScope.launch {
            val stats = _uiState.value.playerStats
            val history = _uiState.value.decisionHistory

            val report = if (_uiState.value.isPresentationMode) {
                OfflineScenarioRepository.generateEndGameReport(stats, history, isLiveAi = false)
            } else {
                try {
                    apiService.generateFinalReport(stats, history)
                } catch (e: Exception) {
                    OfflineScenarioRepository.generateEndGameReport(stats, history, isLiveAi = false)
                }
            }

            prefs.incrementGamesPlayed()
            prefs.unlockAchievement("ach_first_game")
            if (stats.communication >= 75) prefs.unlockAchievement("ach_diplomat")
            if (stats.riskManagement >= 75) prefs.unlockAchievement("ach_adventurer")
            if (stats.empathy >= 80) prefs.unlockAchievement("ach_peacemaker")
            if (stats.boundarySetting >= 80) prefs.unlockAchievement("ach_boundary_shield")
            if (stats.riskManagement >= 80) prefs.unlockAchievement("ach_strategist")
            if (_uiState.value.selectedMode == GameMode.TWIST) {
                prefs.unlockAchievement("ach_twist_master")
            }
            if (stats.communication >= 65 && stats.empathy >= 65 && stats.riskManagement >= 65 && stats.boundarySetting >= 65) {
                prefs.unlockAchievement("ach_balanced_soul")
            }
            if (report.secretEndingUnlocked) {
                prefs.unlockSecretEnding()
            }
            if (_uiState.value.selectedMode == GameMode.CUSTOM) {
                prefs.unlockAchievement("ach_custom_master")
            }
            if (prefs.getTotalGamesPlayed() >= 3) {
                prefs.unlockAchievement("ach_explorer")
            }

            loadAchievements()

            _uiState.update {
                it.copy(
                    finalReport = report,
                    isLoading = false,
                    currentScreen = ScreenState.REPORT
                )
            }
        }
    }

    fun restartGame() {
        startNewGame(_uiState.value.selectedMode, _uiState.value.customTopic.ifEmpty { null })
    }

    fun resetAllData() {
        prefs.clearAllData()
        loadAchievements()
        _uiState.update {
            it.copy(
                totalGamesPlayed = 0,
                currentScreen = ScreenState.WELCOME
            )
        }
    }
}
