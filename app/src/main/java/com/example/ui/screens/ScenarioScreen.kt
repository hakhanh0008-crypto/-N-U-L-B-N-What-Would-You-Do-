package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameScenario
import com.example.data.model.ScenarioChoice
import com.example.ui.components.AiBadge
import com.example.ui.components.CharacterCardRow
import com.example.ui.components.DreamyGradientBackground
import com.example.ui.components.VisualNovelCard
import com.example.ui.theme.*
import com.example.viewmodel.GameUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScenarioScreen(
    uiState: GameUiState,
    onChoiceSelected: (ScenarioChoice) -> Unit,
    onBackToHome: () -> Unit
) {
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Dừng ván chơi hiện tại?") },
            text = { Text("Tiến trình vòng chơi này sẽ không được lưu. Bạn có chắc muốn quay về màn hình chính?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        onBackToHome()
                    }
                ) {
                    Text("Thoát", color = PastelPinkDark, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("Tiếp tục chơi")
                }
            }
        )
    }

    DreamyGradientBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Tình Huống ${uiState.currentRound} / ${uiState.totalRounds}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimaryDark
                            )
                            Text(
                                text = uiState.selectedMode.title,
                                fontSize = 11.sp,
                                color = SoftPurple
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { showExitDialog = true },
                            modifier = Modifier.testTag("scenario_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Quay lại",
                                tint = TextPrimaryDark
                            )
                        }
                    },
                    actions = {
                        AiBadge(
                            isLiveAi = uiState.currentScenario?.isLiveAi == true,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Progress Bar for 5 rounds
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Tiến độ hành trình",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSecondary
                        )
                        Text(
                            text = "${(uiState.currentRound * 100) / uiState.totalRounds}%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PastelPinkDark
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { uiState.currentRound.toFloat() / uiState.totalRounds },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = PastelPinkPrimary,
                        trackColor = Color(0xFFECE4F2),
                    )
                }

                // Loading State or Content
                if (uiState.isLoading || uiState.currentScenario == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(
                                color = PastelPinkPrimary,
                                modifier = Modifier.size(44.dp),
                                strokeWidth = 4.dp
                            )
                            Text(
                                text = uiState.loadingMessage.ifEmpty { "Đang tải tình huống..." },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextPrimaryDark
                            )
                        }
                    }
                } else {
                    val scenario = uiState.currentScenario

                    // Scenario Context Card
                    VisualNovelCard {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = PastelPinkPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = scenario.contextSetting,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SoftPurple
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = scenario.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimaryDark
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Characters row
                        if (scenario.characters.isNotEmpty()) {
                            CharacterCardRow(characters = scenario.characters)
                            Spacer(modifier = Modifier.height(14.dp))
                        }

                        // Situation narrative
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = PastelPinkLight.copy(alpha = 0.5f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderPastel)
                        ) {
                            Text(
                                text = scenario.situation,
                                fontSize = 14.sp,
                                color = TextPrimaryDark,
                                lineHeight = 22.sp,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }

                    // Prompt callout
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = PastelPinkPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "NẾU LÀ BẠN, BẠN SẼ LỰA CHỌN THẾ NÀO?",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftPurple,
                            letterSpacing = 0.5.sp
                        )
                    }

                    // Choices list
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        scenario.choices.forEachIndexed { index, choice ->
                            ChoiceCard(
                                choice = choice,
                                index = index,
                                onClick = { onChoiceSelected(choice) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun ChoiceCard(
    choice: ScenarioChoice,
    index: Int,
    onClick: () -> Unit
) {
    val tag = "choice_option_$index"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(18.dp),
                spotColor = PastelLavender.copy(alpha = 0.25f)
            )
            .border(
                width = 1.dp,
                color = BorderPastel,
                shape = RoundedCornerShape(18.dp)
            )
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .testTag(tag),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.96f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(PastelPinkContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = choice.label,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PastelPinkDark
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = choice.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = choice.description,
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
