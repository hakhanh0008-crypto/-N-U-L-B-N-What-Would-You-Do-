package com.example.data.offline

import com.example.data.model.*

object OfflineScenarioRepository {

    val allAchievements: List<Achievement> = listOf(
        Achievement(
            id = "ach_first_game",
            title = "Bước Chân Đầu Tiên",
            description = "Hoàn thành vòng chơi 5 tình huống đầu tiên.",
            category = "Khởi đầu",
            iconEmoji = "🌱",
            unlockHint = "Hoàn thành bất kỳ 1 vòng chơi (5 tình huống)"
        ),
        Achievement(
            id = "ach_diplomat",
            title = "Nhà Ngoại Giao",
            description = "Hóa giải các xung đột xã hội trong hòa bình và đạt chỉ số Giao Tiếp trên 75 điểm.",
            category = "Giao tiếp & Hòa giải",
            iconEmoji = "🕊️",
            unlockHint = "Đạt chỉ số Giao Tiếp Khéo Léo >= 75"
        ),
        Achievement(
            id = "ach_adventurer",
            title = "Người Phiêu Lưu",
            description = "Dũng cảm lựa chọn những phương án rủi ro cao hoặc chấp nhận thử thách táo bạo.",
            category = "Rủi ro & Can đảm",
            iconEmoji = "🧭",
            unlockHint = "Chọn ít nhất 2 quyết định táo bạo hoặc chỉ số Rủi ro >= 75"
        ),
        Achievement(
            id = "ach_peacemaker",
            title = "Sứ Giả Hòa Bình",
            description = "Luôn đặt cảm xúc và danh dự của người khác lên hàng đầu với Thấu Cảm trên 80 điểm.",
            category = "Giao tiếp & Hòa giải",
            iconEmoji = "💖",
            unlockHint = "Đạt chỉ số Thấu Cảm & Chia Sẻ >= 80"
        ),
        Achievement(
            id = "ach_boundary_shield",
            title = "Tấm Khiên Thép",
            description = "Dũng cảm bảo vệ giá trị và ranh giới cá nhân, đạt chỉ số Ranh Giới trên 80 điểm.",
            category = "Ranh giới cá nhân",
            iconEmoji = "🛡️",
            unlockHint = "Đạt chỉ số Thiết Lập Ranh Giới >= 80"
        ),
        Achievement(
            id = "ach_strategist",
            title = "Chiến Lược Gia Tỉnh Táo",
            description = "Tư duy phòng ngừa rủi ro sắc sảo, tính toán trước các hệ lụy thực tế.",
            category = "Rủi ro & Can đảm",
            iconEmoji = "♟️",
            unlockHint = "Đạt chỉ số Quản Trị Rủi Ro >= 80"
        ),
        Achievement(
            id = "ach_twist_master",
            title = "Vượt Qua Nghịch Lý",
            description = "Hoàn thành chế độ 'Cú twist bất ngờ' và đối mặt trọn vẹn 5 tình huống nan giải.",
            category = "Chế độ chơi",
            iconEmoji = "⚡",
            unlockHint = "Hoàn thành 5 tình huống trong chế độ Cú twist bất ngờ"
        ),
        Achievement(
            id = "ach_balanced_soul",
            title = "Bậc Thầy Cân Bằng",
            description = "Dung hòa xuất sắc cả 4 phẩm chất: Giao tiếp, Thấu cảm, Rủi ro và Ranh giới (mỗi chỉ số >= 65).",
            category = "Toàn diện",
            iconEmoji = "⚖️",
            unlockHint = "Tất cả 4 chỉ số đều đạt trên 65 điểm trong một ván chơi"
        ),
        Achievement(
            id = "ach_secret_ending",
            title = "Người Khai Sáng Bí Mật",
            description = "Khai mở cái kết bí mật ẩn giấu trong game bằng sự thông thái nội tâm.",
            category = "Bí mật",
            iconEmoji = "🌟",
            unlockHint = "Mở khóa Secret Ending khi kết thúc ván chơi"
        ),
        Achievement(
            id = "ach_custom_master",
            title = "Đạo Diễn Cuộc Đời",
            description = "Tự tạo tình huống và hoàn thành kịch bản do chính bạn đặt đề bài cho AI.",
            category = "Sáng tạo",
            iconEmoji = "🎭",
            unlockHint = "Hoàn thành 1 ván chơi trong chế độ Tự tạo tình huống"
        ),
        Achievement(
            id = "ach_explorer",
            title = "Nhà Thám Hiểm Xã Hội",
            description = "Trải nghiệm và hoàn thành ít nhất 3 vòng chơi khác nhau trong game.",
            category = "Kỳ cựu",
            iconEmoji = "🏆",
            unlockHint = "Chơi và hoàn thành từ 3 vòng chơi trở lên"
        )
    )

    fun getScenariosForMode(mode: GameMode, customTopic: String? = null): List<GameScenario> {
        return when (mode) {
            GameMode.DAILY -> getDailyScenarios()
            GameMode.TEAMWORK -> getTeamworkScenarios()
            GameMode.TWIST -> getTwistScenarios()
            GameMode.CUSTOM -> getCustomFallbackScenarios(customTopic ?: "Tình huống tự do")
        }
    }

    private fun getDailyScenarios(): List<GameScenario> = listOf(
        GameScenario(
            id = "daily_1",
            roundNumber = 1,
            totalRounds = 5,
            mode = GameMode.DAILY,
            title = "Mượn Máy Ảnh Làm Rơi",
            contextSetting = "Quán trà sữa gần trường vào chiều thứ Bảy",
            characters = listOf(
                CharacterProfile("Quỳnh Anh", "Bạn thân từ cấp 3", "🌸", "Nhạy cảm, thích nhiếp ảnh"),
                CharacterProfile("Bảo", "Bạn cùng lớp", "🎒", "Thẳng thắn, hơi bộc trực")
            ),
            situation = "Quỳnh Anh mượn chiếc máy ảnh mirrorless đắt tiền bạn vừa mua bằng tiền làm thêm hè. Khi trả, bạn phát hiện ống kính bị trầy một vết khá rõ, nhưng Quỳnh Anh chỉ cười xòa: 'Hôm nọ tớ để trên bàn chắc ai quẹt vào ấy, kính vẫn chụp bình thường mà!'.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Thẳng thắn & Rõ ràng",
                    description = "Chỉ rõ vết xước ngay lúc đó, giải thích giá trị của máy và yêu cầu cùng mang ra tiệm thẩm định chi phí sửa chữa.",
                    impact = ChoiceImpact(communication = 15, boundarySetting = 20, riskManagement = 15, empathy = -5)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Nhẹ nhàng tâm tình",
                    description = "Không làm bạn ngượng giữa quán, đợi khi về nhắn tin chia sẻ cảm xúc xót của và đề xuất mỗi người hỗ trợ một nửa phí dán lại kính bảo vệ.",
                    impact = ChoiceImpact(communication = 15, boundarySetting = 10, empathy = 20, riskManagement = 5)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Ngậm bồ hòn làm ngọt",
                    description = "Cười gượng bỏ qua để giữ hòa khí vì sợ mất bạn, tự mình đem máy đi sửa trong âm thầm nhưng bụng ấm ức.",
                    impact = ChoiceImpact(communication = -15, boundarySetting = -20, riskManagement = -10, empathy = 5)
                ),
                ScenarioChoice(
                    id = "c4",
                    label = "D",
                    title = "Rút kinh nghiệm hài hước",
                    description = "Nói nửa đùa nửa thật: 'Thế này là phạt bữa buffet lẩu rồi đấy nhé!', sau đó ra quy tắc từ nay không cho mượn đồ điện tử nữa.",
                    impact = ChoiceImpact(communication = 10, boundarySetting = 15, riskManagement = 10, empathy = 10)
                )
            )
        ),
        GameScenario(
            id = "daily_2",
            roundNumber = 2,
            totalRounds = 5,
            mode = GameMode.DAILY,
            title = "Bạn Cùng Phòng Dắt Người Yêu Về Khuya",
            contextSetting = "Phòng trọ sinh viên 25m² lúc 23:30",
            characters = listOf(
                CharacterProfile("Huy", "Bạn cùng phòng", "🎸", "Sống phóng khoáng, vô tư"),
                CharacterProfile("Thảo", "Người yêu của Huy", "✨", "Thỉnh thoảng ghé chơi")
            ),
            situation = "Mai bạn có bài thi học kỳ lúc 7h sáng. Huy bất ngờ dẫn người yêu về phòng lúc nửa đêm, bật đèn, cười đùa và mở nhạc nhỏ nấu mì khuya. Đây đã là lần thứ ba trong tuần bạn chịu đựng cảnh này.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Ngồi dậy đặt ranh giới dứt khoát",
                    description = "Bật dậy, yêu cầu Huy và bạn gái tôn trọng giờ ngủ của bạn, nhắc lại nội quy phòng trọ không đón khách qua đêm.",
                    impact = ChoiceImpact(communication = 10, boundarySetting = 25, riskManagement = 10, empathy = -5)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Nhắn tin riêng khéo léo",
                    description = "Gửi tin nhắn riêng cho Huy ngay trên giường: 'Mai tao thi quan trọng, hai đứa giữ yên lặng giúp tao ngủ một chút nhé, mai mình nói chuyện sau'.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 15, riskManagement = 15, empathy = 15)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Đeo tai nghe chịu đựng",
                    description = "Trùm chăn, đeo nút tai chống ồn cố ngủ, hôm sau vẫn tỏ ra bình thường vì ngại va chạm.",
                    impact = ChoiceImpact(communication = -15, boundarySetting = -20, riskManagement = -15, empathy = 0)
                ),
                ScenarioChoice(
                    id = "c4",
                    label = "D",
                    title = "Họp phòng giải quyết tận gốc",
                    description = "Ráng ngủ đêm nay, sáng mai trước khi đi thi hẹn tối họp phòng để lập thỏa ước bằng văn bản về khách tới chơi.",
                    impact = ChoiceImpact(communication = 15, boundarySetting = 20, riskManagement = 20, empathy = 10)
                )
            )
        ),
        GameScenario(
            id = "daily_3",
            roundNumber = 3,
            totalRounds = 5,
            mode = GameMode.DAILY,
            title = "Chia Tiền Ăn Uống 'Bất Công'",
            contextSetting = "Bữa tiệc sinh nhật bạn tại nhà hàng nướng",
            characters = listOf(
                CharacterProfile("Tuấn", "Người khởi xướng", "🍖", "Thích ăn uống sang chảnh"),
                CharacterProfile("Hà", "Sinh viên tiết kiệm", "🍵", "Chỉ uống nước lọc")
            ),
            situation = "Cả nhóm 8 người đi ăn. Một vài bạn gọi rất nhiều rượu vang và món đắt tiền, trong khi bạn chỉ gọi một phần cơm bò đơn giản vì đang kẹt tiền cuối tháng. Đến khi thanh toán, Tuấn đề xuất 'Campuchia chia đều đầu người 550k/người!'.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Lên tiếng tính riêng phần rượu",
                    description = "Cười tươi nhưng rõ ràng: 'Đồ ăn mình chia đều được, nhưng tiền rượu ai uống thì chia riêng nhé, hôm nay mình không uống rượu!'.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 20, riskManagement = 15, empathy = 10)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Bấm bụng chuyển khoản đủ",
                    description = "Ngại phá hỏng bầu không khí vui vẻ nên gật đầu chuyển khoản 550k, dù tuần sau phải ăn mì gói.",
                    impact = ChoiceImpact(communication = -10, boundarySetting = -25, riskManagement = -20, empathy = 15)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Rủ Hà cùng tính lại",
                    description = "Thấy Hà cũng bối rối, bạn chủ động lấy hóa đơn làm thủ quỹ: 'Để tớ cộng riêng món chung và món riêng cho công bằng nhé!'.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 15, riskManagement = 20, empathy = 25)
                )
            )
        ),
        GameScenario(
            id = "daily_4",
            roundNumber = 4,
            totalRounds = 5,
            mode = GameMode.DAILY,
            title = "Bí Mật Bị Tiết Lộ Trong Bàn Trà",
            contextSetting = "Bàn ăn căn-tin trường đại học",
            characters = listOf(
                CharacterProfile("Mai", "Bạn thân", "🤫", "Vui tính nhưng hơi buôn chuyện"),
                CharacterProfile("Đức", "Crush bí mật của bạn", "👀", "Ngồi ngay bàn bên cạnh")
            ),
            situation = "Hôm trước bạn tâm sự với Mai rằng bạn đang thích thầm Đức. Hôm nay đang ngồi ăn căn-tin đông người, Mai lỡ miệng trêu to: 'Đức kìa, nhìn đắm đuối thế không ra chào người yêu tương lai đi à?'. Cả bàn quay lại nhìn bạn ái ngại.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Hóa giải bằng sự tự tin",
                    description = "Cười tự nhiên vẫy tay chào Đức: 'Mai trêu ấy mà, chào ông nhé!', giữ thể diện cho cả ba rồi hẹn nói chuyện riêng với Mai sau.",
                    impact = ChoiceImpact(communication = 25, boundarySetting = 15, riskManagement = 20, empathy = 15)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Nổi giận đùng đùng",
                    description = "Đỏ mặt tía tai, lớn tiếng trách Mai kém duyên rồi đứng dậy bỏ đi khỏi bàn ăn.",
                    impact = ChoiceImpact(communication = -15, boundarySetting = 10, riskManagement = -20, empathy = -15)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Đối thoại nghiêm túc riêng tư",
                    description = "Nuốt giận qua câu chuyện khác, chiều về nhắn tin thẳng thắn: 'Lời nói đùa của bạn khiến mình rất tổn thương và mất tin tưởng'.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 25, riskManagement = 15, empathy = 10)
                )
            )
        ),
        GameScenario(
            id = "daily_5",
            roundNumber = 5,
            totalRounds = 5,
            mode = GameMode.DAILY,
            title = "Lời Rủ Rê Đầu Tư Tiền Ảo 'Chắc Thắng'",
            contextSetting = "Quán cà phê tầng thượng",
            characters = listOf(
                CharacterProfile("Thành", "Anh bạn khóa trên quen biết", "📈", "Đang làm tài chính, hào nhoáng"),
                CharacterProfile("Bạn", "Sinh viên năm 3", "🎯", "Có một khoản tiết kiệm 15 triệu")
            ),
            situation = "Thành khoe vừa mua ô tô nhờ một sàn giao dịch tiền điện tử mới nổi và khuyên bạn: 'Đưa anh 10 triệu ủy thác, cam kết lãi 25%/tháng, cơ hội chỉ có trong tuần này cho anh em thân thiết!'.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Từ chối lịch sự nhưng kiên quyết",
                    description = "Cảm ơn nhưng từ chối: 'Em chỉ đầu tư vào thứ em thực sự hiểu rõ, tiền này em để đóng học phí kỳ tới'.",
                    impact = ChoiceImpact(communication = 15, boundarySetting = 25, riskManagement = 25, empathy = 5)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Hỏi dồn các giấy tờ pháp lý",
                    description = "Yêu cầu xem hợp đồng cam kết pháp lý, giấy phép hoạt động và điều khoản rủi ro khiến Thành lúng túng lảng tránh.",
                    impact = ChoiceImpact(communication = 10, boundarySetting = 20, riskManagement = 25, empathy = 0)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Thử một ít vì nể mặt",
                    description = "Sợ mất lòng đàn anh nên chấp nhận gửi 3 triệu 'gọi là ủng hộ', thầm mong kiếm được chút lời.",
                    impact = ChoiceImpact(communication = -5, boundarySetting = -20, riskManagement = -25, empathy = 10)
                )
            )
        )
    )

    private fun getTeamworkScenarios(): List<GameScenario> = listOf(
        GameScenario(
            id = "team_1",
            roundNumber = 1,
            totalRounds = 5,
            mode = GameMode.TEAMWORK,
            title = "Phân Chia Đề Tài & 'Gánh Team'",
            contextSetting = "Phòng tự học thư viện trường",
            characters = listOf(
                CharacterProfile("Nam", "Thành viên ham chơi", "🎮", "Ít khi đến lớp"),
                CharacterProfile("Phương", "Trưởng nhóm cẩn thận", "📑", "Luôn lo lắng bài điểm kém")
            ),
            situation = "Nhóm 4 người được giao bài tập lớn 30 trang. Nam lập tức chọn phần 'tìm tài liệu mở đầu' (chỉ 2 trang đơn giản), đùn đẩy hai chương lý thuyết và phân tích dữ liệu nặng nhất cho bạn và Phương.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Phân rã công việc theo trọng số",
                    description = "Lập bảng Kanban/Rubric chấm điểm rõ ràng: ai làm ít nội dung thì phải phụ trách làm Slide thuyết trình và in ấn báo cáo.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 20, riskManagement = 25, empathy = 10)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Nhận hết để đảm bảo điểm A",
                    description = "Nghĩ 'muốn việc tốt thì tự làm', bạn nhận luôn phần khó để đỡ phải nhắc nhở Nam mệt mỏi.",
                    impact = ChoiceImpact(communication = -10, boundarySetting = -25, riskManagement = -10, empathy = 10)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Báo cáo giảng viên đổi nhóm",
                    description = "Bực mình vì sự lười biếng của Nam, bạn lập tức đề nghị Phương cùng lên gặp thầy để xin loại Nam ra khỏi nhóm.",
                    impact = ChoiceImpact(communication = 5, boundarySetting = 20, riskManagement = -10, empathy = -20)
                ),
                ScenarioChoice(
                    id = "c4",
                    label = "D",
                    title = "Thảo luận tìm thế mạnh của Nam",
                    description = "Hỏi Nam có kỹ năng thiết kế video hoặc thuyết trình không để giao đúng sở trường, đồng thời yêu cầu deadline trung gian.",
                    impact = ChoiceImpact(communication = 25, boundarySetting = 15, riskManagement = 15, empathy = 20)
                )
            )
        ),
        GameScenario(
            id = "team_2",
            roundNumber = 2,
            totalRounds = 5,
            mode = GameMode.TEAMWORK,
            title = "Thành Viên 'Mất Tích' Trước Giờ G",
            contextSetting = "Nhóm Zalo lúc 22:00, deadline là 08:00 sáng mai",
            characters = listOf(
                CharacterProfile("Khánh", "Thành viên phụ trách kết luận", "🏖️", "Đã xem tin nhắn nhưng không trả lời"),
                CharacterProfile("Hương", "Bạn cùng nhóm", "⚡", "Đang phát hoảng vì thiếu bài")
            ),
            situation = "Còn 10 tiếng là đến hạn nộp báo cáo. Khánh phụ trách tổng hợp và viết giải pháp vẫn chưa nộp file, gọi điện không bắt máy, dù trên Facebook vừa thấy đăng ảnh đi xem phim.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Phân chia viết thay & hạ điểm đóng góp",
                    description = "Bạn và Hương chia nhau viết nốt phần việc của Khánh để cứu bài, đồng thời lập biên bản nhóm ghi nhận Khánh đóng góp 0%.",
                    impact = ChoiceImpact(communication = 15, boundarySetting = 25, riskManagement = 25, empathy = 0)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Nhắn tối hậu thư có thời hạn",
                    description = "Gửi thông báo vào nhóm: 'Đến 23h30 nếu bạn không gửi bài, nhóm sẽ tự viết và loại tên bạn khỏi bìa báo cáo'.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 20, riskManagement = 20, empathy = 5)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Thức trắng làm hộ và vẫn để tên bạn",
                    description = "Làm giúp hết vì nghĩ 'bạn bè cùng lớp sau này còn nhìn mặt nhau', chấp nhận chịu thiệt.",
                    impact = ChoiceImpact(communication = -15, boundarySetting = -25, riskManagement = -15, empathy = 10)
                )
            )
        ),
        GameScenario(
            id = "team_3",
            roundNumber = 3,
            totalRounds = 5,
            mode = GameMode.TEAMWORK,
            title = "Phát Hiện Bài Làm Bị 'Đạo Văn'",
            contextSetting = "Khi kiểm tra bài qua phần mềm Turnitin",
            characters = listOf(
                CharacterProfile("Linh", "Thành viên nộp bài", "📋", "Hay vội vàng, đối phó"),
                CharacterProfile("Bạn", "Người kiểm tra cuối cùng", "🔍", "Kỹ tính, sợ bị kỷ luật")
            ),
            situation = "Turnitin báo đỏ 68% trùng lặp trong phần của Linh, hóa ra Linh copy nguyên xi từ một luận văn khóa trước trên mạng. Nếu nộp bài này, cả nhóm có nguy cơ nhận 0 điểm vì vi phạm liêm chính học thuật.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Gặp riêng yêu cầu viết lại 100%",
                    description = "Gửi ảnh chụp kết quả Turnitin cho Linh, giải thích mức độ nghiêm trọng và yêu cầu Linh paraphrase, trích dẫn chuẩn chỉ trước 3h sáng.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 20, riskManagement = 25, empathy = 15)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Tự viết lại để an tâm",
                    description = "Không tin Linh có thể viết lại kịp nên tự thức đêm viết lại toàn bộ phần đó để loại trừ rủi ro.",
                    impact = ChoiceImpact(communication = 5, boundarySetting = -15, riskManagement = 20, empathy = 10)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Bóc phốt trong group nhóm",
                    description = "Đăng thẳng lên nhóm chat chỉ trích gay gắt khiến Linh tự ái đòi rút khỏi nhóm.",
                    impact = ChoiceImpact(communication = -20, boundarySetting = 15, riskManagement = -15, empathy = -25)
                )
            )
        ),
        GameScenario(
            id = "team_4",
            roundNumber = 4,
            totalRounds = 5,
            mode = GameMode.TEAMWORK,
            title = "Câu Hỏi Đột Ngột Của Giảng Viên",
            contextSetting = "Hội trường bảo vệ tiểu luận cuối kỳ",
            characters = listOf(
                CharacterProfile("Thầy Tuấn", "Giảng viên phản biện", "👓", "Sắc sảo, nghiêm khắc"),
                CharacterProfile("An", "Bạn thuyết trình lúng túng", "🎤", "Nói sai số liệu")
            ),
            situation = "Thầy Tuấn hỏi xoáy một câu cực khó vào phần số liệu sai của An. An đứng hình không trả lời được. Thầy hỏi sang bạn: 'Em là trưởng nhóm, em giải thích phần này thế nào hay nhóm chỉ làm qua loa?'.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Bình tĩnh bổ sung và nhận trách nhiệm chung",
                    description = "Bước lên micro: 'Thưa thầy, bạn An vừa có chút hồi hộp. Thay mặt nhóm em xin phép làm rõ logic đằng sau bảng số liệu này...'.",
                    impact = ChoiceImpact(communication = 25, boundarySetting = 10, riskManagement = 20, empathy = 25)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Đổ lỗi cho cá nhân làm phần đó",
                    description = "Nói thẳng: 'Dạ thưa thầy, phần này do bạn An phụ trách nghiên cứu độc lập nên em không nắm sâu ạ'.",
                    impact = ChoiceImpact(communication = -15, boundarySetting = 10, riskManagement = -15, empathy = -25)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Thẳng thắn thừa nhận thiếu sót và hướng khắc phục",
                    description = "Thừa nhận số liệu chưa chặt chẽ và trình bày ngay phương án hiệu chỉnh, ghi điểm nhờ tinh thần cầu thị.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 15, riskManagement = 25, empathy = 15)
                )
            )
        ),
        GameScenario(
            id = "team_5",
            roundNumber = 5,
            totalRounds = 5,
            mode = GameMode.TEAMWORK,
            title = "Đánh Giá Điểm Peer-Review Cuối Kỳ",
            contextSetting = "Hệ thống nộp bảng điểm đóng góp cá nhân",
            characters = listOf(
                CharacterProfile("Tập thể nhóm", "4 thành viên", "👥", "Mỗi người một mức đóng góp")
            ),
            situation = "Hệ thống yêu cầu mỗi thành viên bí mật chấm điểm % đóng góp của các bạn khác. Một bạn làm việc rất ít nhắn tin xin bạn chấm cho 100% để giữ học bổng.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Chấm khách quan theo đúng thực tế",
                    description = "Chấm đúng 60% theo khối lượng bạn ấy làm, từ chối cả nể vì sự công bằng với các thành viên cày cuốc.",
                    impact = ChoiceImpact(communication = 15, boundarySetting = 25, riskManagement = 20, empathy = 5)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Nói chuyện trước khi chấm",
                    description = "Gọi điện riêng giải thích cho bạn: 'Mình rất tiếc cho học bổng của bạn, nhưng mình không thể chấm gian dối được. Mình sẽ để bạn 75% như mức nỗ lực tối đa'.",
                    impact = ChoiceImpact(communication = 25, boundarySetting = 20, riskManagement = 20, empathy = 20)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Thả tay cho 100% cho xong chuyện",
                    description = "Tặc lưỡi cho bạn 100% vì sợ bị ghét bỏ, dù cảm thấy cắn rứt với những bạn thức đêm gánh team.",
                    impact = ChoiceImpact(communication = -15, boundarySetting = -25, riskManagement = -20, empathy = 5)
                )
            )
        )
    )

    private fun getTwistScenarios(): List<GameScenario> = listOf(
        GameScenario(
            id = "twist_1",
            roundNumber = 1,
            totalRounds = 5,
            mode = GameMode.TWIST,
            title = "Bắt Gặp Người Yêu Của Bạn Thân",
            contextSetting = "Trung tâm thương mại vào tối thứ Bảy",
            characters = listOf(
                CharacterProfile("Long", "Người yêu của bạn thân bạn (Trang)", "🕶️", "Đang nắm tay người khác"),
                CharacterProfile("Trang", "Bạn thân nhất của bạn", "💔", "Đang ở nhà ôn thi")
            ),
            situation = "Bạn tình cờ thấy Long nắm tay ôm eo một cô gái lạ vào rạp chiếu phim, trong khi vừa 1 tiếng trước Trang khoe với bạn là Long đang tăng ca ở công ty. Bạn chụp được một bức ảnh rõ mặt.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Gặp riêng Long đối chất trước",
                    description = "Tiến đến trước mặt Long chào hỏi lịch sự để Long biết bạn đã thấy, sau đó yêu cầu Long tự thú nhận với Trang trong 24 giờ.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 20, riskManagement = 25, empathy = 15)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Gửi ngay ảnh cho Trang",
                    description = "Gửi thẳng ảnh và định vị cho Trang để bạn thân biết ngay sự thật mà không giấu giếm một phút nào.",
                    impact = ChoiceImpact(communication = 10, boundarySetting = 15, riskManagement = -10, empathy = 20)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Gặp trực tiếp Trang vào ngày mai",
                    description = "Không gửi tin nhắn đột ngột gây sốc, hẹn Trang đi cà phê ngày mai, ngồi cạnh bạn để an ủi và đưa bằng chứng.",
                    impact = ChoiceImpact(communication = 25, boundarySetting = 15, riskManagement = 20, empathy = 25)
                ),
                ScenarioChoice(
                    id = "c4",
                    label = "D",
                    title = "Im lặng coi như không thấy",
                    description = "Tự nhủ chuyện tình cảm người khác mình can thiệp dễ bị mang tiếng 'chia rẽ', xóa ảnh đi.",
                    impact = ChoiceImpact(communication = -20, boundarySetting = -15, riskManagement = 10, empathy = -25)
                )
            )
        ),
        GameScenario(
            id = "twist_2",
            roundNumber = 2,
            totalRounds = 5,
            mode = GameMode.TWIST,
            title = "Lỗi Phần Mềm Công Ty Thực Tập",
            contextSetting = "Văn phòng công ty công nghệ vào tuần cuối thực tập",
            characters = listOf(
                CharacterProfile("Anh Hùng", "Mentor hướng dẫn trực tiếp", "💻", "Sắp được thăng chức"),
                CharacterProfile("Bạn", "Thực tập sinh", "🎯", "Đang mong được giữ lại làm chính thức")
            ),
            situation = "Bạn phát hiện một lỗi logic nghiêm trọng trong mã nguồn chuẩn bị release ra khách hàng lớn, có thể làm rò rỉ dữ liệu người dùng. Bạn báo cáo với mentor Hùng, nhưng anh ấy gạt đi: 'Bỏ qua đi em, báo lên là hủy đợt ra mắt, anh em mất thưởng quý đấy!'.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Tự viết bản phân tích rủi ro & giải pháp",
                    description = "Thức đêm viết sẵn đoạn mã vá lỗi và tài liệu giải trình ngắn gọn, gửi lại cho anh Hùng để chứng minh sửa rất nhanh và an toàn.",
                    impact = ChoiceImpact(communication = 25, boundarySetting = 20, riskManagement = 25, empathy = 20)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Báo cáo vượt cấp lên Giám đốc Kỹ thuật (CTO)",
                    description = "Vì đạo đức nghề nghiệp, bạn gửi email ẩn danh hoặc gặp trực tiếp CTO để cảnh báo nguy cơ rò rỉ.",
                    impact = ChoiceImpact(communication = 5, boundarySetting = 25, riskManagement = 20, empathy = -10)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Nghe lời mentor im lặng",
                    description = "Nghĩ mình chỉ là thực tập sinh thấp cổ bé họng, không nên làm mất lòng người quyết định cơ hội việc làm của mình.",
                    impact = ChoiceImpact(communication = -15, boundarySetting = -20, riskManagement = -25, empathy = -10)
                )
            )
        ),
        GameScenario(
            id = "twist_3",
            roundNumber = 3,
            totalRounds = 5,
            mode = GameMode.TWIST,
            title = "Người Từng 'Quỵt Nợ' Hỏi Mượn Tiền Cấp Cứu",
            contextSetting = "Cuộc gọi Messenger lúc 1h sáng",
            characters = listOf(
                CharacterProfile("Bình", "Bạn cũ thời cấp 3", "🏥", "Khóc lóc van xin"),
                CharacterProfile("Bạn", "Chủ nợ cũ 2 triệu chưa đòi được", "💭", "Vừa nhận lương làm thêm")
            ),
            situation = "Bình từng mượn bạn 2 triệu từ 2 năm trước rồi lặn mất tăm. Đêm nay Bình gọi điện khóc lóc, bảo mẹ đang cấp cứu ở bệnh viện thiếu 5 triệu đóng viện phí, xin bạn cứu giúp và hứa trả cả gốc lẫn lãi.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Đề nghị chuyển khoản trực tiếp cho viện phí",
                    description = "Yêu cầu số tài khoản thu viện phí hoặc hóa đơn của bệnh viện để chuyển thẳng hỗ trợ một phần, không chuyển vào tài khoản cá nhân.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 25, riskManagement = 25, empathy = 20)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Tặng luôn 1 triệu coi như làm từ thiện",
                    description = "Chuyển 1 triệu và nói: 'Khoản này coi như tớ giúp bác, không cần trả. Còn tiền cũ khi nào bạn có thì gửi lại sau'.",
                    impact = ChoiceImpact(communication = 15, boundarySetting = 15, riskManagement = 15, empathy = 25)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Từ chối thẳng thừng và cúp máy",
                    description = "Nghĩ là chiêu trò lừa đảo mượn tiền, bạn nhắc lại món nợ cũ và chặn số ngay lập tức.",
                    impact = ChoiceImpact(communication = -10, boundarySetting = 25, riskManagement = 20, empathy = -20)
                )
            )
        ),
        GameScenario(
            id = "twist_4",
            roundNumber = 4,
            totalRounds = 5,
            mode = GameMode.TWIST,
            title = "Hai Lời Mời Trùng Giờ Quan Trọng",
            contextSetting = "Tối thứ Bảy tuần tới",
            characters = listOf(
                CharacterProfile("Bố mẹ", "Gia đình ở quê lên thăm", "🏡", "Cả năm mới lên một lần"),
                CharacterProfile("Nhóm dự án khởi nghiệp", "Các nhà đầu tư thiên thần", "💼", "Cơ hội nhận 100 triệu tài trợ")
            ),
            situation = "Bố mẹ bí mật bắt xe từ quê lên thành phố nấu cơm sinh nhật cho bạn, muốn cả nhà sum vầy. Đúng lúc đó, bạn nhận thông báo cuộc hẹn thuyết trình gọi vốn quan trọng nhất năm của nhóm bạn bị dời đột ngột vào đúng 19h tối đó.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Đưa bố mẹ cùng đến sự kiện / quán gần đó",
                    description = "Giải thích với bố mẹ, mời bố mẹ đi cùng đến ngồi quán cà phê sang trọng cạnh nơi thuyết trình, sau khi pitching 1 tiếng thì cả nhà cùng đi ăn mừng.",
                    impact = ChoiceImpact(communication = 25, boundarySetting = 15, riskManagement = 20, empathy = 25)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Uỷ quyền cho đồng sáng lập thuyết trình",
                    description = "Chuẩn bị kỹ tài liệu và slide cho bạn đồng sáng lập thay mình trình bày, dành trọn buổi tối quý giá cho bố mẹ.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 20, riskManagement = 10, empathy = 25)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Bỏ gia đình đi thuyết trình một mình",
                    description = "Nói dối bận việc học đột xuất, vội vã đi thuyết trình để bố mẹ ở phòng trọ một mình ăn cơm nguội.",
                    impact = ChoiceImpact(communication = -15, boundarySetting = 10, riskManagement = 15, empathy = -25)
                )
            )
        ),
        GameScenario(
            id = "twist_5",
            roundNumber = 5,
            totalRounds = 5,
            mode = GameMode.TWIST,
            title = "Chiếc Vé Xem Ca Nhạc Cuối Cùng",
            contextSetting = "Cửa soát vé concert BlackPink / Anh Trai Vượt Ngàn Chông Gai",
            characters = listOf(
                CharacterProfile("Phương", "Người bạn đã săn vé cùng bạn 3 tháng", "🎟️", "Bị rơi mất vé ở cổng"),
                CharacterProfile("Bảo vệ", "Người soát vé nghiêm khắc", "👮", "Chỉ còn 1 chỗ trống")
            ),
            situation = "Hai bạn cùng thần tượng một ban nhạc từ 5 năm trước. Đến cổng soát vé, Phương phát hiện vé của mình bị kẻ gian móc túi mất, bạn cầm trên tay chiếc vé duy nhất của mình. Buổi diễn chỉ còn 5 phút là bắt đầu.",
            choices = listOf(
                ScenarioChoice(
                    id = "c1",
                    label = "A",
                    title = "Ở lại ngoài cổng cùng bạn",
                    description = "Không ngần ngại bán lại vé hoặc không vào nữa, cùng Phương ngồi ngoài sảnh nghe nhạc và ăn kem: 'Bạn bè quan trọng hơn concert'.",
                    impact = ChoiceImpact(communication = 20, boundarySetting = 10, riskManagement = 15, empathy = 30)
                ),
                ScenarioChoice(
                    id = "c2",
                    label = "B",
                    title = "Vào xem nhưng livestream và mua quà đền bù",
                    description = "Thảo luận nhanh: 'Tớ vào xem rồi livestream liên tục cho cậu nhé, mai tớ bao cậu vé xem phim IMAX bù!'.",
                    impact = ChoiceImpact(communication = 15, boundarySetting = 20, riskManagement = 15, empathy = 10)
                ),
                ScenarioChoice(
                    id = "c3",
                    label = "C",
                    title = "Cùng nhau kiên trì tìm ban tổ chức",
                    description = "Dùng mã xác nhận thanh toán ngân hàng và email đăng ký chính chủ để nhờ trưởng ban tổ chức giải quyết, dù có thể trễ 20 phút đầu.",
                    impact = ChoiceImpact(communication = 25, boundarySetting = 20, riskManagement = 25, empathy = 20)
                )
            )
        )
    )

    private fun getCustomFallbackScenarios(customTopic: String): List<GameScenario> {
        return (1..5).map { round ->
            GameScenario(
                id = "custom_$round",
                roundNumber = round,
                totalRounds = 5,
                mode = GameMode.CUSTOM,
                title = "Giai đoạn $round: $customTopic",
                contextSetting = "Bối cảnh thực tế liên quan đến: $customTopic",
                characters = listOf(
                    CharacterProfile("Nhân vật liên quan", "Người đồng hành", "🎭", "Có quyền lợi đối lập"),
                    CharacterProfile("Bạn", "Người ra quyết định", "💡", "Đang cân nhắc các lựa chọn")
                ),
                situation = "Bạn đang phải xử lý một mâu thuẫn then chốt trong chủ đề '$customTopic' ở giai đoạn $round. Các bên đang nhìn vào phản ứng của bạn để quyết định bước tiếp theo.",
                choices = listOf(
                    ScenarioChoice(
                        id = "c1",
                        label = "A",
                        title = "Đối thoại cởi mở & Đặt ranh giới",
                        description = "Trình bày rõ quan điểm cá nhân, lắng nghe đối phương và đưa ra đề xuất cùng có lợi.",
                        impact = ChoiceImpact(communication = 20, boundarySetting = 20, riskManagement = 15, empathy = 15)
                    ),
                    ScenarioChoice(
                        id = "c2",
                        label = "B",
                        title = "Ưu tiên cảm xúc & Hòa giải",
                        description = "Đặt sự bình an của mối quan hệ lên hàng đầu, nhún nhường bước đầu để hạ nhiệt căng thẳng.",
                        impact = ChoiceImpact(communication = 15, boundarySetting = 5, riskManagement = 10, empathy = 25)
                    ),
                    ScenarioChoice(
                        id = "c3",
                        label = "C",
                        title = "Chiến lược thận trọng & Bảo vệ bản thân",
                        description = "Lập bằng chứng rõ ràng, xin thêm thời gian suy nghĩ để tránh đưa ra quyết định khi cảm xúc lấn át.",
                        impact = ChoiceImpact(communication = 10, boundarySetting = 25, riskManagement = 25, empathy = 5)
                    ),
                    ScenarioChoice(
                        id = "c4",
                        label = "D",
                        title = "Tìm kiếm cố vấn trung gian",
                        description = "Nhờ một người có uy tín và góc nhìn khách quan phân giải thay vì tự mình đôi co.",
                        impact = ChoiceImpact(communication = 15, boundarySetting = 15, riskManagement = 20, empathy = 15)
                    )
                ),
                isLiveAi = false
            )
        }
    }

    fun getOfflineFeedback(choice: ScenarioChoice, isLiveAi: Boolean = false): AiFeedback {
        val impact = choice.impact
        val strengths = when {
            impact.communication >= 20 -> "Khả năng diễn đạt gãy gọn, chủ động làm chủ cuộc đối thoại mà không làm bầu không khí bị nghẹt thở."
            impact.empathy >= 20 -> "Lòng trắc ẩn sâu sắc, biết đặt mình vào hoàn cảnh của đối phương để hành xử nhân văn và vị tha."
            impact.boundarySetting >= 20 -> "Ranh giới cá nhân rất vững vàng; bạn dũng cảm bảo vệ giá trị và quyền lợi chính đáng của mình."
            impact.riskManagement >= 20 -> "Tư duy phòng ngừa rủi ro sắc sảo, tính toán trước các hệ lụy thực tế thay vì cảm tính nhất thời."
            else -> "Lựa chọn hài hòa, biết dung hòa giữa các yếu tố để tránh xung đột bùng phát."
        }

        val drawbacks = when {
            impact.boundarySetting < 0 -> "Có xu hướng nhường nhịn quá mức hoặc 'ngại va chạm', dễ tích tụ ấm ức lâu dài về sau."
            impact.empathy < 0 -> "Hơi cứng nhắc hoặc lạnh lùng, có thể khiến đối phương cảm thấy bị công kích hoặc mất mặt."
            impact.communication < 0 -> "Thiếu sự chia sẻ minh bạch; đối phương có thể không hiểu được thiện chí hay suy nghĩ thật của bạn."
            impact.riskManagement < 0 -> "Tiềm ẩn rủi ro phát sinh rắc rối lớn hơn nếu tình huống không diễn ra theo kịch bản lý tưởng."
            else -> "Đòi hỏi bạn phải kiên trì theo đuổi thỏa thuận đến cùng để không bị trôi vào quên lãng."
        }

        val shortTerm = "Trong 24-48 giờ tới: Bầu không khí tạm thời ổn định, đối phương đã hiểu được tín hiệu và vị thế của bạn."
        val longTerm = "Về lâu dài: Bạn xây dựng được hình tượng một người đáng tin cậy, có nguyên tắc sống rõ ràng nhưng không thiếu sự bao dung."
        val perspective = "Từ góc nhìn của người khác: Họ có thể ban đầu hơi bất ngờ hoặc bối rối, nhưng sẽ tôn trọng sự đàng hoàng và nhất quán của bạn."

        return AiFeedback(
            choiceId = choice.id,
            choiceLabel = choice.label,
            choiceSummary = choice.title,
            strengths = strengths,
            drawbacks = drawbacks,
            shortTermConsequence = shortTerm,
            longTermConsequence = longTerm,
            alternativePerspective = perspective,
            statImpact = impact,
            isLiveAi = isLiveAi
        )
    }

    fun generateEndGameReport(stats: PlayerStats, decisions: List<DecisionHistoryItem>, isLiveAi: Boolean = false): EndGameReport {
        val c = stats.communication
        val e = stats.empathy
        val r = stats.riskManagement
        val b = stats.boundarySetting

        val isBalanced = c in 60..85 && e in 60..85 && r in 60..85 && b in 60..85
        val secretUnlocked = (e >= 75 && b >= 75) || isBalanced

        val (archetype, subtitle, desc) = when {
            isBalanced -> Triple(
                "Bậc Thầy Cân Bằng Cảm Xúc",
                "Hiếm có & Thấu đáo",
                "Bạn sở hữu sự điềm tĩnh và cân bằng hiếm có. Bạn vừa biết lắng nghe thấu cảm, vừa biết vạch ra ranh giới rõ ràng mà không làm tổn thương người khác."
            )
            c >= e && c >= r && c >= b -> Triple(
                "Nhà Ngoại Giao Quyết Đoán",
                "Giao tiếp sắc bén & Cởi mở",
                "Vũ khí mạnh nhất của bạn là ngôn từ và sự chân thành. Bạn luôn tìm ra cách nói giảm, nói khéo nhưng vẫn chạm đúng trọng tâm vấn đề."
            )
            e >= c && e >= r && e >= b -> Triple(
                "Người Chữa Lành Thấu Cảm",
                "Ấm áp & Trắc ẩn",
                "Bạn luôn đặt cảm xúc và danh dự của người khác lên trên hết. Bạn là chỗ dựa tinh thần tuyệt vời cho bạn bè trong những lúc sóng gió."
            )
            b >= c && b >= e && b >= r -> Triple(
                "Người Bảo Vệ Ranh Giới",
                "Bản lĩnh & Nguyên tắc",
                "Bạn là người có cột sống thép: không ngại nói 'Không', không để người khác lợi dụng lòng tốt và luôn rõ ràng trong các mối quan hệ."
            )
            else -> Triple(
                "Chiến Lược Gia Tỉnh Táo",
                "Thực tế & Cẩn trọng",
                "Bạn phân tích mọi việc bằng cái đầu lạnh, luôn cân nhắc rủi ro và tìm kiếm phương án an toàn nhất cho bản thân và tập thể."
            )
        }

        val strengths = mutableListOf<String>()
        if (c >= 60) strengths.add("Khả năng truyền đạt thông điệp khó một cách hòa nhã")
        if (e >= 60) strengths.add("Nhạy bén với tổn thương và cảm xúc ngầm của người khác")
        if (r >= 60) strengths.add("Tỉnh táo trước các lời rủ rê và bẫy rủi ro xã hội")
        if (b >= 60) strengths.add("Dũng cảm từ chối các đòi hỏi vô lý mà không tự dằn vặt")
        if (strengths.isEmpty()) strengths.add("Khả năng thích ứng linh hoạt theo từng hoàn cảnh cụ thể")

        val blindSpots = mutableListOf<String>()
        if (b < 45) blindSpots.add("Đôi khi vì sợ mất hòa khí mà gánh vác phần thiệt thòi về mình")
        if (e < 45) blindSpots.add("Có thể quá lý trí khiến người khác cảm thấy thiếu sự đồng cảm")
        if (c < 45) blindSpots.add("Có xu hướng giữ im lặng chịu đựng thay vì thẳng thắn chia sẻ")
        if (r < 45) blindSpots.add("Dễ tin tưởng vào lời hứa miệng mà thiếu cam kết bằng chứng")
        if (blindSpots.isEmpty()) blindSpots.add("Cần lưu ý không để việc suy nghĩ quá nhiều làm chậm hành động tức thời")

        val advice = listOf(
            "Trong làm việc nhóm: Hãy thống nhất quy tắc và deadline trung gian bằng văn bản ngay từ ngày đầu tiên.",
            "Trong tình bạn: Sự chân thành đi kèm ranh giới lành mạnh sẽ giữ tình bạn bền vững hơn là sự nhẫn nhịn mù quáng.",
            "Khi đối mặt biến cố: Dành cho bản thân 15 phút hạ nhiệt cảm xúc trước khi gửi tin nhắn hoặc đưa ra quyết định quan trọng."
        )

        return EndGameReport(
            archetypeTitle = archetype,
            archetypeSubtitle = subtitle,
            archetypeDescription = desc,
            strengths = strengths,
            blindSpots = blindSpots,
            practicalAdvice = advice,
            finalStats = stats,
            secretEndingUnlocked = secretUnlocked,
            secretEndingTitle = if (secretUnlocked) "Secret Ending: Trí Tuệ Vô Tướng" else null,
            secretEndingStory = if (secretUnlocked) "Bạn đã mở khóa kết cục ẩn: Không có lựa chọn nào là sai tuyệt đối. Cuộc sống là một chuỗi đối thoại giữa sự tử tế và lòng kiên định. Bạn đã tìm thấy sự tự do trong chính tâm hồn mình!" else null,
            isLiveAi = isLiveAi
        )
    }
}
