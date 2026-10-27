package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.dialogs.AchievementsDialog
import com.example.ui.dialogs.AiExplanationDialog
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.ScreenState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GameAppNavigation()
                }
            }
        }
    }
}

@Composable
fun GameAppNavigation(viewModel: GameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Crossfade(targetState = uiState.currentScreen, label = "screenTransition") { screen ->
        when (screen) {
            ScreenState.WELCOME -> WelcomeScreen(
                uiState = uiState,
                onStartClick = { viewModel.navigateTo(ScreenState.MODE_SELECT) },
                onHowAiWorksClick = { viewModel.setShowAiExplanation(true) },
                onAchievementsClick = { viewModel.navigateTo(ScreenState.ACHIEVEMENTS) },
                onTogglePresentationMode = { viewModel.togglePresentationMode() }
            )

            ScreenState.MODE_SELECT -> ModeSelectionScreen(
                onModeSelect = { mode -> viewModel.selectMode(mode) },
                onBackClick = { viewModel.navigateTo(ScreenState.WELCOME) }
            )

            ScreenState.CUSTOM_TOPIC -> CustomTopicScreen(
                onTopicSubmit = { topic -> viewModel.startCustomGame(topic) },
                onBackClick = { viewModel.navigateTo(ScreenState.MODE_SELECT) }
            )

            ScreenState.SCENARIO -> ScenarioScreen(
                uiState = uiState,
                onChoiceSelected = { choice -> viewModel.submitChoice(choice) },
                onBackToHome = { viewModel.navigateTo(ScreenState.WELCOME) }
            )

            ScreenState.FEEDBACK -> FeedbackScreen(
                uiState = uiState,
                onProceed = { viewModel.proceedToNext() }
            )

            ScreenState.REPORT -> FinalReportScreen(
                uiState = uiState,
                onPlayAgain = { viewModel.restartGame() },
                onBackToHome = { viewModel.navigateTo(ScreenState.WELCOME) },
                onResetData = { viewModel.resetAllData() }
            )

            ScreenState.ACHIEVEMENTS -> AchievementsScreen(
                uiState = uiState,
                onBackClick = { viewModel.navigateTo(ScreenState.WELCOME) }
            )
        }
    }

    if (uiState.showAiExplanation) {
        AiExplanationDialog(
            onDismiss = { viewModel.setShowAiExplanation(false) }
        )
    }

    if (uiState.showAchievementsDialog) {
        AchievementsDialog(
            achievements = uiState.achievements,
            totalGames = uiState.totalGamesPlayed,
            onDismiss = { viewModel.setShowAchievementsDialog(false) }
        )
    }
}
