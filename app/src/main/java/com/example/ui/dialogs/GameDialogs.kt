package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Achievement
import com.example.ui.theme.*

@Composable
fun AiExplanationDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(1.5.dp, BorderPastel, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(PastelPinkContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = PastelPinkDark,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "Cách AI Hoạt Động",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_ai_dialog_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Đóng", tint = TextSecondary)
                    }
                }

                Text(
                    text = "Hệ thống sử dụng mô hình ngôn ngữ lớn Google Gemini được tinh chỉnh theo phương pháp Prompt Engineering có cấu trúc JSON:",
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )

                AiExplanationCard(
                    step = "1",
                    title = "Khởi tạo tình huống không có đáp án tuyệt đối",
                    content = "AI thiết kế các mâu thuẫn xã hội đa chiều nơi mỗi lựa chọn đều có cái giá phải trả và cơ hội đi kèm, tránh thiên lệch đạo đức cứng nhắc."
                )

                AiExplanationCard(
                    step = "2",
                    title = "Đánh giá đa chiều & Không phán xét",
                    content = "Thay vì gán nhãn Đúng/Sai, AI tách biệt: Điểm mạnh, Rủi ro tiềm ẩn, Hậu quả trước mắt (24-48h), Tác động dài hạn và Góc nhìn thấu cảm từ đối phương."
                )

                AiExplanationCard(
                    step = "3",
                    title = "Thích ứng diễn biến theo quyết định trước",
                    content = "Lịch sử các quyết định được đưa vào ngữ cảnh (context window) của lượt kế tiếp, tạo nên một chuỗi sự kiện phản ánh chính xác phong cách người chơi."
                )

                AiExplanationCard(
                    step = "4",
                    title = "Chế độ Ngoại tuyến & Bảo mật thông tin",
                    content = "Toàn bộ kịch bản và phân tích đều có kho dự phòng ngoại tuyến chất lượng cao. Thông tin lựa chọn không bị lưu trữ ra ngoài máy người dùng."
                )

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PastelPinkPrimary)
                ) {
                    Text(text = "Đã Hiểu", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun AiExplanationCard(
    step: String,
    title: String,
    content: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = PastelLavenderLight.copy(alpha = 0.6f),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderPastel)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(PastelLavenderDark),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = step,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = content,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun AchievementsDialog(
    achievements: List<Achievement>,
    totalGames: Int,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .clip(RoundedCornerShape(24.dp))
                .border(1.5.dp, BorderPastel, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFFF3E0)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = AccentGold,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Bộ Sưu Tập Danh Hiệu",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimaryDark
                            )
                            Text(
                                text = "Mở khóa: ${achievements.count { it.isUnlocked }} / ${achievements.size} • $totalGames ván chơi",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_achievements_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Đóng", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(achievements) { ach ->
                        AchievementItemCard(achievement = ach)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PastelPinkPrimary)
                ) {
                    Text(text = "Đóng", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AchievementItemCard(achievement: Achievement) {
    val isUnlocked = achievement.isUnlocked
    val bg = if (isUnlocked) PastelPinkLight else Color(0xFFF5F5F5)
    val border = if (isUnlocked) PastelPinkPrimary.copy(alpha = 0.5f) else Color(0xFFE0E0E0)

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = bg,
        border = androidx.compose.foundation.BorderStroke(1.dp, border)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(if (isUnlocked) Color.White else Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isUnlocked) achievement.iconEmoji else "🔒",
                    fontSize = 20.sp
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = achievement.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isUnlocked) TextPrimaryDark else Color.Gray
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = achievement.description,
                    fontSize = 11.sp,
                    color = if (isUnlocked) TextSecondary else Color.Gray,
                    lineHeight = 15.sp
                )
            }

            if (isUnlocked) {
                Text(
                    text = "Đã mở",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = PastelPinkDark
                )
            }
        }
    }
}
