package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
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
import com.example.ui.components.DreamyGradientBackground
import com.example.ui.components.VisualNovelCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopicScreen(
    onTopicSubmit: (String) -> Unit,
    onBackClick: () -> Unit
) {
    BackHandler { onBackClick() }

    var topicInput by remember { mutableStateOf("") }
    val suggestedTopics = listOf(
        "Người yêu cũ nhắn tin mời dự đám cưới",
        "Bị sếp thực tập mắng oan trước cuộc họp",
        "Bạn thân vay tiền chữa bệnh nhưng từng quỵt nợ",
        "Phát hiện bạn cùng bàn quay cóp trong kỳ thi quốc gia",
        "Gia đình phản đối quyết định bỏ ngành học để khởi nghiệp"
    )

    DreamyGradientBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Tự Tạo Tình Huống AI",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.testTag("custom_back_button")
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
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                VisualNovelCard {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = PastelPinkPrimary)
                        Text(
                            text = "Bạn muốn AI mô phỏng tình huống nào?",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hãy nhập một mâu thuẫn xã hội, rắc rối đời thường hoặc tình thế khó xử mà bạn từng trăn trở. AI sẽ viết kịch bản 5 giai đoạn thích ứng theo lựa chọn của bạn.",
                        fontSize = 13.sp,
                        color = TextSecondary,
                        lineHeight = 18.sp
                    )
                }

                OutlinedTextField(
                    value = topicInput,
                    onValueChange = { topicInput = it },
                    placeholder = {
                        Text("Ví dụ: Bị bạn thân hiểu lầm khi nói đỡ cho người khác...")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(16.dp))
                        .testTag("custom_topic_input"),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = PastelPinkPrimary,
                        unfocusedBorderColor = BorderPastel
                    ),
                    minLines = 3,
                    maxLines = 5
                )

                Button(
                    onClick = {
                        val chosen = topicInput.ifBlank { "Tình huống giao tiếp khó xử trong cuộc sống" }
                        onTopicSubmit(chosen)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(8.dp, RoundedCornerShape(26.dp), spotColor = PastelPinkPrimary)
                        .testTag("create_scenario_button"),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PastelPinkPrimary)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Tạo Kịch Bản & Bắt Đầu Chơi",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Hoặc chọn nhanh gợi ý tiêu biểu:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SoftPurple
                )

                suggestedTopics.forEach { topic ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { topicInput = topic },
                        shape = RoundedCornerShape(14.dp),
                        color = Color.White.copy(alpha = 0.9f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderPastel)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(text = "💡", fontSize = 16.sp)
                            Text(
                                text = topic,
                                fontSize = 13.sp,
                                color = TextPrimaryDark,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
