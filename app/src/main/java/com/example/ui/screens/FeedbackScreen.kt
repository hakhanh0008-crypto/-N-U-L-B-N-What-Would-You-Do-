package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AiBadge
import com.example.ui.components.DreamyGradientBackground
import com.example.ui.components.StatChangePill
import com.example.ui.components.VisualNovelCard
import com.example.ui.theme.*
import com.example.viewmodel.GameUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    uiState: GameUiState,
    onProceed: () -> Unit
) {
    // Pressing system back proceeds to next or confirms
    BackHandler { onProceed() }

    val feedback = uiState.currentFeedback
    val selectedChoice = uiState.selectedChoice

    DreamyGradientBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Phân Tích AI & Hậu Quả",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                    },
                    actions = {
                        AiBadge(
                            isLiveAi = feedback?.isLiveAi == true,
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
                // Choice Recap Card
                if (selectedChoice != null) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        color = PastelPinkLight,
                        border = androidx.compose.foundation.BorderStroke(1.dp, PastelPinkPrimary.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(PastelPinkPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = selectedChoice.label,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column {
                                Text(
                                    text = "Lựa chọn của bạn:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SoftPurple
                                )
                                Text(
                                    text = selectedChoice.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimaryDark
                                )
                            }
                        }
                    }
                }

                // Stat Changes Pill
                if (feedback != null) {
                    StatChangePill(impact = feedback.statImpact)
                }

                // AI Feedback Detail Cards
                if (feedback != null) {
                    VisualNovelCard {
                        // Advantages / Strengths
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AccentMint, modifier = Modifier.size(18.dp))
                            Text(
                                text = "ĐIỂM SÁNG & THẾ MẠNH",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentMint
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = feedback.strengths,
                            fontSize = 13.sp,
                            color = TextPrimaryDark,
                            lineHeight = 19.sp
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = BorderPastel
                        )

                        // Disadvantages / Drawbacks
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = AccentCoral, modifier = Modifier.size(18.dp))
                            Text(
                                text = "THÁCH THỨC & RỦI RO TIỀM ẨN",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentCoral
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = feedback.drawbacks,
                            fontSize = 13.sp,
                            color = TextPrimaryDark,
                            lineHeight = 19.sp
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = BorderPastel
                        )

                        // Short-term & Long-term Consequences
                        Text(
                            text = "⏳ HẬU QUẢ NGẮN HẠN & DÀI HẠN",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftPurple
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "• ${feedback.shortTermConsequence}",
                            fontSize = 13.sp,
                            color = TextPrimaryDark,
                            lineHeight = 19.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "• ${feedback.longTermConsequence}",
                            fontSize = 13.sp,
                            color = TextPrimaryDark,
                            lineHeight = 19.sp
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = BorderPastel
                        )

                        // Alternative Perspective / Empathy
                        Text(
                            text = "👀 GÓC NHÌN TỪ ĐỐI PHƯƠNG",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PastelLavenderDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = feedback.alternativePerspective,
                            fontSize = 13.sp,
                            color = TextSecondary,
                            lineHeight = 19.sp
                        )
                    }
                }

                // Continue Button
                val isLastRound = uiState.currentRound >= uiState.totalRounds
                val buttonText = if (isLastRound) "Xem Báo Cáo Tổng Kết Chung Cuộc" else "Tiếp Tục Tình Huống ${uiState.currentRound + 1}"

                Button(
                    onClick = onProceed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .shadow(8.dp, RoundedCornerShape(27.dp), spotColor = PastelPinkPrimary)
                        .testTag("next_scenario_button"),
                    shape = RoundedCornerShape(27.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PastelPinkPrimary)
                ) {
                    Text(
                        text = buttonText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
