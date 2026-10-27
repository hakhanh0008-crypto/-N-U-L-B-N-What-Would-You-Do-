package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CharacterProfile
import com.example.data.model.ChoiceImpact
import com.example.data.model.PlayerStats
import com.example.ui.theme.*

@Composable
fun DreamyGradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            PastelPinkLight,
            PastelLavenderLight,
            SoftBlushCream
        )
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        content()
    }
}

@Composable
fun VisualNovelCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White.copy(alpha = 0.92f),
    borderColor: Color = BorderPastel,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = PastelLavender.copy(alpha = 0.35f),
                ambientColor = PastelPinkPrimary.copy(alpha = 0.2f)
            )
            .border(
                width = 1.5.dp,
                brush = Brush.linearGradient(
                    colors = listOf(borderColor, PastelPinkLight)
                ),
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            content()
        }
    }
}

@Composable
fun AiBadge(
    isLiveAi: Boolean,
    modifier: Modifier = Modifier
) {
    val (bg, textColor, text, icon) = if (isLiveAi) {
        Tuple4(
            Color(0xFFE8F5E9),
            Color(0xFF2E7D32),
            "Gemini AI Trực Tiếp",
            "✨"
        )
    } else {
        Tuple4(
            Color(0xFFEDE7F6),
            Color(0xFF5E35B1),
            "Ngoại Tuyến / Demo",
            "💡"
        )
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = bg,
        border = androidx.compose.foundation.BorderStroke(1.dp, textColor.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = icon, fontSize = 12.sp)
            Text(
                text = text,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
        }
    }
}

private data class Tuple4<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)

@Composable
fun CharacterCardRow(characters: List<CharacterProfile>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        characters.forEach { char ->
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                color = PastelLavenderLight.copy(alpha = 0.8f),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderPastel)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = char.avatarEmoji, fontSize = 18.sp)
                    }
                    Column {
                        Text(
                            text = char.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark,
                            maxLines = 1
                        )
                        Text(
                            text = char.role,
                            fontSize = 10.sp,
                            color = TextSecondary,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatsOverviewCard(stats: PlayerStats) {
    VisualNovelCard {
        Text(
            text = "📊 BẢNG CHỈ SỐ PHẢN XẠ XÃ HỘI",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = SoftPurple,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(14.dp))
        StatMeter(
            label = "Giao tiếp khéo léo",
            value = stats.communication,
            color = AccentSky,
            icon = "💬"
        )
        Spacer(modifier = Modifier.height(10.dp))
        StatMeter(
            label = "Thấu cảm & Chia sẻ",
            value = stats.empathy,
            color = PastelPinkPrimary,
            icon = "💖"
        )
        Spacer(modifier = Modifier.height(10.dp))
        StatMeter(
            label = "Quản trị rủi ro",
            value = stats.riskManagement,
            color = AccentMint,
            icon = "🧭"
        )
        Spacer(modifier = Modifier.height(10.dp))
        StatMeter(
            label = "Thiết lập ranh giới",
            value = stats.boundarySetting,
            color = PastelLavenderDark,
            icon = "🛡️"
        )
    }
}

@Composable
fun StatMeter(
    label: String,
    value: Int,
    color: Color,
    icon: String
) {
    val animatedProgress by animateFloatAsState(
        targetValue = (value.coerceIn(0, 100)) / 100f,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "statAnim"
    )

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = icon, fontSize = 14.sp)
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimaryDark
                )
            }
            Text(
                text = "$value/100",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFECE4F2))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(color.copy(alpha = 0.7f), color)
                        )
                    )
            )
        }
    }
}

@Composable
fun StatChangePill(
    impact: ChoiceImpact,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SingleImpactTag(name = "Giao tiếp", value = impact.communication, color = AccentSky)
        SingleImpactTag(name = "Thấu cảm", value = impact.empathy, color = PastelPinkPrimary)
        SingleImpactTag(name = "Rủi ro", value = impact.riskManagement, color = AccentMint)
        SingleImpactTag(name = "Ranh giới", value = impact.boundarySetting, color = PastelLavenderDark)
    }
}

@Composable
private fun SingleImpactTag(
    name: String,
    value: Int,
    color: Color
) {
    if (value == 0) return
    val sign = if (value > 0) "+$value" else "$value"
    val isPositive = value > 0
    val bg = if (isPositive) color.copy(alpha = 0.15f) else Color(0xFFFFEBEE)
    val textColor = if (isPositive) color else Color(0xFFD32F2F)

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = bg
    ) {
        Text(
            text = "$name $sign",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
        )
    }
}
