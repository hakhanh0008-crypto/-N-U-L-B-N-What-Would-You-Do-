package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AiBadge
import com.example.ui.components.DreamyGradientBackground
import com.example.ui.components.StatsOverviewCard
import com.example.ui.components.VisualNovelCard
import com.example.ui.theme.*
import com.example.viewmodel.GameUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinalReportScreen(
    uiState: GameUiState,
    onPlayAgain: () -> Unit,
    onBackToHome: () -> Unit,
    onResetData: () -> Unit
) {
    BackHandler { onBackToHome() }

    val report = uiState.finalReport
    var showResetDialog by remember { mutableStateOf(false) }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Đặt lại toàn bộ dữ liệu?") },
            text = { Text("Thao tác này sẽ xóa số vòng chơi và các danh hiệu thành tựu đã lưu trong máy. Bạn có chắc chắn không?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showResetDialog = false
                        onResetData()
                    }
                ) {
                    Text("Xác nhận xóa", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }

    DreamyGradientBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Báo Cáo Phản Xạ Xã Hội",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                    },
                    actions = {
                        AiBadge(
                            isLiveAi = report?.isLiveAi == true,
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Celebration Header
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(PastelPinkPrimary, PastelLavender)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "👑", fontSize = 32.sp)
                }

                Text(
                    text = "HOÀN THÀNH 5/5 TÌNH HUỐNG!",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftPurple,
                    letterSpacing = 1.sp
                )

                if (report != null) {
                    // Archetype Banner Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(10.dp, RoundedCornerShape(24.dp), spotColor = PastelPinkPrimary)
                            .border(2.dp, Color.White, RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = PastelPinkContainer.copy(alpha = 0.9f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = report.archetypeTitle,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PastelPinkDark,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "“${report.archetypeSubtitle}”",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SoftPurple,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = report.archetypeDescription,
                                fontSize = 13.sp,
                                color = TextPrimaryDark,
                                textAlign = TextAlign.Center,
                                lineHeight = 19.sp
                            )
                        }
                    }

                    // Secret Ending Card (If unlocked)
                    if (report.secretEndingUnlocked && report.secretEndingTitle != null) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFFFF8E1),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, AccentGold)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(text = "🌟", fontSize = 20.sp)
                                    Text(
                                        text = report.secretEndingTitle,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFE65100)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = report.secretEndingStory.orEmpty(),
                                    fontSize = 12.sp,
                                    color = TextPrimaryDark,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }

                    // Radar / Progress Bars
                    StatsOverviewCard(stats = report.finalStats)

                    // Strengths & Blind Spots
                    VisualNovelCard {
                        // Strengths
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Stars, contentDescription = null, tint = AccentMint, modifier = Modifier.size(18.dp))
                            Text(
                                text = "ĐIỂM MẠNH NỔI BẬT CỦA BẠN",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentMint
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        report.strengths.forEach { s ->
                            Text(
                                text = "• $s",
                                fontSize = 13.sp,
                                color = TextPrimaryDark,
                                lineHeight = 18.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = BorderPastel)

                        // Blind spots
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Visibility, contentDescription = null, tint = AccentCoral, modifier = Modifier.size(18.dp))
                            Text(
                                text = "VÙNG MÙ & ĐIỂM CẦN LƯU Ý",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentCoral
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        report.blindSpots.forEach { b ->
                            Text(
                                text = "• $b",
                                fontSize = 13.sp,
                                color = TextPrimaryDark,
                                lineHeight = 18.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = BorderPastel)

                        // Practical advice
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = AccentGold, modifier = Modifier.size(18.dp))
                            Text(
                                text = "GỢI Ý ỨNG XỬ THỰC TẾ TRONG ĐỜI SỐNG",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF57C00)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        report.practicalAdvice.forEach { a ->
                            Text(
                                text = "• $a",
                                fontSize = 13.sp,
                                color = TextPrimaryDark,
                                lineHeight = 18.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }

                    // Nuanced Science / Psychology Disclaimer
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = Color.White.copy(alpha = 0.8f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderPastel)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "ℹ️", fontSize = 14.sp)
                            Text(
                                text = "Lưu ý khoa học: Báo cáo này phản ánh khuynh hướng phản xạ xã hội của bạn qua 5 tình huống mô phỏng để tự chiêm nghiệm, không mang tính chẩn đoán tâm lý học lâm sàng hay khẳng định tính cách cố định.",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                // Play Again Button
                Button(
                    onClick = onPlayAgain,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .shadow(8.dp, RoundedCornerShape(27.dp), spotColor = PastelPinkPrimary)
                        .testTag("play_again_button"),
                    shape = RoundedCornerShape(27.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PastelPinkPrimary)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Chơi Lại Vòng Khác",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Return Home & Reset Options
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onBackToHome,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("home_button"),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = SoftPurple),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, PastelLavender)
                    ) {
                        Text(text = "Về Trang Chủ", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }

                    OutlinedButton(
                        onClick = { showResetDialog = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("reset_data_button"),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderPastel)
                    ) {
                        Text(text = "Đặt Lại Dữ Liệu", fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
