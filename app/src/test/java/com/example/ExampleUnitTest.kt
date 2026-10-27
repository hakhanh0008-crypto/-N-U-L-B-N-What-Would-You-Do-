package com.example

import com.example.data.model.ChoiceImpact
import com.example.data.model.DecisionHistoryItem
import com.example.data.model.GameMode
import com.example.data.model.PlayerStats
import com.example.data.offline.OfflineScenarioRepository
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testOfflineScenariosCompleteness() {
        GameMode.values().forEach { mode ->
            val scenarios = OfflineScenarioRepository.getScenariosForMode(mode)
            assertEquals("Chế độ $mode phải có đúng 5 tình huống", 5, scenarios.size)
            scenarios.forEachIndexed { index, s ->
                assertEquals("Vòng của scenario phải khớp", index + 1, s.roundNumber)
                assertTrue("Mỗi tình huống phải có ít nhất 3 lựa chọn", s.choices.size >= 3)
            }
        }
    }

    @Test
    fun testPlayerStatsApplyImpact() {
        val initial = PlayerStats(communication = 50, empathy = 50, riskManagement = 50, boundarySetting = 50)
        val impact = ChoiceImpact(communication = 20, empathy = -10, riskManagement = 15, boundarySetting = 25)
        val updated = initial.applyImpact(impact)

        assertEquals(70, updated.communication)
        assertEquals(40, updated.empathy)
        assertEquals(65, updated.riskManagement)
        assertEquals(75, updated.boundarySetting)
    }

    @Test
    fun testPlayerStatsClamped() {
        val stats = PlayerStats(communication = 95, empathy = 15, riskManagement = 50, boundarySetting = 50)
        val extremeImpact = ChoiceImpact(communication = 30, empathy = -50, riskManagement = 0, boundarySetting = 0)
        val updated = stats.applyImpact(extremeImpact)

        assertEquals(100, updated.communication)
        assertEquals(10, updated.empathy) // minimum is 10
    }

    @Test
    fun testEndGameReportGeneration() {
        val highEmpathyStats = PlayerStats(communication = 50, empathy = 85, riskManagement = 40, boundarySetting = 40)
        val history = listOf(
            DecisionHistoryItem("Tình huống 1", "A", "Nhẹ nhàng tâm sự", "Thấu cảm")
        )
        val report = OfflineScenarioRepository.generateEndGameReport(highEmpathyStats, history)

        assertNotNull(report.archetypeTitle)
        assertTrue(report.strengths.isNotEmpty())
        assertTrue(report.blindSpots.isNotEmpty())
        assertTrue(report.practicalAdvice.isNotEmpty())
    }

    @Test
    fun testSecretEndingUnlockCondition() {
        val balancedStats = PlayerStats(communication = 70, empathy = 75, riskManagement = 70, boundarySetting = 75)
        val report = OfflineScenarioRepository.generateEndGameReport(balancedStats, emptyList())

        assertTrue("Secret ending should be unlocked when empathy and boundary are both high", report.secretEndingUnlocked)
        assertNotNull(report.secretEndingTitle)
    }
}
