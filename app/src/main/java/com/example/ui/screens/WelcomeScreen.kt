package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PresentToAll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.DreamyGradientBackground
import com.example.ui.components.VisualNovelCard
import com.example.ui.theme.*
import com.example.viewmodel.GameUiState

@Composable
fun WelcomeScreen(
    uiState: GameUiState,
    onStartClick: () -> Unit,
    onHowAiWorksClick: () -> Unit,
    onAchievementsClick: () -> Unit,
    onTogglePresentationMode: () -> Unit
) {
    DreamyGradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Title Header with sparkling badges
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = PastelPinkLight,
                border = androidx.compose.foundation.BorderStroke(1.dp, PastelPinkPrimary.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Sparkle",
                        tint = PastelPinkDark,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Interactive Life-Simulation AI",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PastelPinkDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "NẾU LÀ BẠN?",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimaryDark,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )

            Text(
                text = "What Would You Do? — Game Tương Tác Xã Hội",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = SoftPurple,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Hero Illustration Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .shadow(12.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .border(2.dp, Color.White, RoundedCornerShape(24.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.vn_hero_banner),
                    contentDescription = "Hình minh họa Visual Novel khuôn viên trường",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Soft subtle overlay gradient at bottom
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.45f)),
                                startY = 100f
                            )
                        )
                )

                Text(
                    text = "🌸 Đối mặt tình huống • Ra quyết định • Thấu hiểu bản thân",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Intro Narrative Card
            VisualNovelCard {
                Text(
                    text = "Chào mừng bạn đến với giảng đường & cuộc sống xã hội!",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tại đây, bạn sẽ trải qua 5 tình huống éo le nhưng cực kỳ quen thuộc: từ mượn đồ làm hỏng, sinh tồn deadline làm việc nhóm đến những ngã ba đường bất ngờ.\n\nTrí tuệ nhân tạo Gemini sẽ phân tích góc nhìn, hệ quả ngắn-dài hạn và phản chiếu phong cách ứng xử của bạn mà không hề phán xét đúng sai.",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Presentation Mode Banner
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                color = if (uiState.isPresentationMode) PastelPinkContainer else Color.White.copy(alpha = 0.8f),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (uiState.isPresentationMode) PastelPinkPrimary else BorderPastel
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(PastelLavenderContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PresentToAll,
                                contentDescription = "Demo Mode",
                                tint = SoftPurple,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Chế độ Thuyết trình Lớp học",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimaryDark
                            )
                            Text(
                                text = if (uiState.isPresentationMode) "Đang bật: Phản hồi tức thì, không độ trễ" else "Bật khi demo báo cáo môn học",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                    }
                    Switch(
                        checked = uiState.isPresentationMode,
                        onCheckedChange = { onTogglePresentationMode() },
                        modifier = Modifier.testTag("presentation_mode_toggle")
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Start Button
            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(10.dp, RoundedCornerShape(28.dp), spotColor = PastelPinkPrimary)
                    .testTag("start_game_button"),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PastelPinkPrimary,
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Bắt Đầu Chơi Ngay",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons Row: How AI Works & Achievements
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onHowAiWorksClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("how_ai_works_button"),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = SoftPurple),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, PastelLavender)
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Cách AI hoạt động", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = onAchievementsClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("achievements_button"),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimaryDark),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, BorderPastel)
                ) {
                    Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = AccentGold, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Thành tựu (${uiState.achievements.count { it.isUnlocked }}/${uiState.achievements.size})", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Đã hoàn thành: ${uiState.totalGamesPlayed} vòng chơi • Không cần tạo tài khoản",
                fontSize = 11.sp,
                color = TextTertiary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
