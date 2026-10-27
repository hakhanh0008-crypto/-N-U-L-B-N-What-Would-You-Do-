package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Achievement
import com.example.ui.components.DreamyGradientBackground
import com.example.ui.theme.*
import com.example.viewmodel.GameUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(
    uiState: GameUiState,
    onBackClick: () -> Unit
) {
    BackHandler { onBackClick() }

    val achievements = uiState.achievements
    val unlockedCount = achievements.count { it.isUnlocked }
    val totalCount = achievements.size

    val categories = listOf("Tất cả", "Giao tiếp & Hòa giải", "Rủi ro & Can đảm", "Ranh giới cá nhân", "Chế độ & Bí mật")
    var selectedCategory by remember { mutableStateOf("Tất cả") }

    val filteredList = when (selectedCategory) {
        "Tất cả" -> achievements
        "Chế độ & Bí mật" -> achievements.filter { it.category in listOf("Chế độ chơi", "Bí mật", "Sáng tạo") }
        else -> achievements.filter { it.category == selectedCategory }
    }

    DreamyGradientBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Bộ Sưu Tập Thành Tựu",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.testTag("achievements_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Quay lại",
                                tint = TextPrimaryDark
                            )
                        }
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
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                // Header Progress Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(22.dp), spotColor = PastelLavender)
                        .border(1.5.dp, BorderPastel, RoundedCornerShape(22.dp)),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(AccentGold, Color(0xFFFFD54F))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Tiến độ hoàn thành: $unlockedCount / $totalCount",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimaryDark
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Đã chơi ${uiState.totalGamesPlayed} vòng mô phỏng",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { if (totalCount > 0) unlockedCount.toFloat() / totalCount else 0f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = PastelPinkPrimary,
                                trackColor = Color(0xFFEDE7F6)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Category Filter Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { cat ->
                        val isSelected = selectedCategory == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PastelPinkPrimary,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Achievements List
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(filteredList) { ach ->
                        AchievementDetailCard(achievement = ach)
                    }
                }
            }
        }
    }
}

@Composable
fun AchievementDetailCard(achievement: Achievement) {
    val isUnlocked = achievement.isUnlocked
    val bg = if (isUnlocked) Color.White.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.65f)
    val border = if (isUnlocked) PastelPinkPrimary.copy(alpha = 0.6f) else BorderLight

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isUnlocked) 6.dp else 2.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = if (isUnlocked) PastelPinkPrimary.copy(alpha = 0.3f) else Color.Transparent
            )
            .border(1.dp, border, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = bg)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (isUnlocked) PastelPinkContainer else Color(0xFFF0F0F0)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isUnlocked) {
                    Text(text = achievement.iconEmoji, fontSize = 24.sp)
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Chưa mở",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = achievement.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isUnlocked) TextPrimaryDark else Color.Gray
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isUnlocked) Color(0xFFE8F5E9) else Color(0xFFF5F5F5)
                    ) {
                        Text(
                            text = if (isUnlocked) "Đã mở khóa" else "Chưa mở",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isUnlocked) Color(0xFF2E7D32) else Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = achievement.description,
                    fontSize = 12.sp,
                    color = if (isUnlocked) TextSecondary else Color.Gray,
                    lineHeight = 17.sp
                )

                if (achievement.unlockHint.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "🎯 Điều kiện: ${achievement.unlockHint}",
                        fontSize = 11.sp,
                        color = if (isUnlocked) SoftPurple else Color(0xFF9E9E9E),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
