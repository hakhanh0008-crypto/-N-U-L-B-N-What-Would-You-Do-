package com.example.data.local

import android.content.Context
import android.content.SharedPreferences

class GamePreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("neu_la_ban_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOTAL_GAMES = "total_games_played"
        private const val KEY_UNLOCKED_ACHIEVEMENTS = "unlocked_achievements"
        private const val KEY_SECRET_ENDINGS_COUNT = "secret_endings_count"
    }

    fun getTotalGamesPlayed(): Int = prefs.getInt(KEY_TOTAL_GAMES, 0)

    fun incrementGamesPlayed() {
        val current = getTotalGamesPlayed()
        prefs.edit().putInt(KEY_TOTAL_GAMES, current + 1).apply()
    }

    fun getUnlockedAchievementIds(): Set<String> {
        return prefs.getStringSet(KEY_UNLOCKED_ACHIEVEMENTS, emptySet()) ?: emptySet()
    }

    fun unlockAchievement(id: String) {
        val current = getUnlockedAchievementIds().toMutableSet()
        current.add(id)
        prefs.edit().putStringSet(KEY_UNLOCKED_ACHIEVEMENTS, current).apply()
    }

    fun unlockSecretEnding() {
        val current = prefs.getInt(KEY_SECRET_ENDINGS_COUNT, 0)
        prefs.edit().putInt(KEY_SECRET_ENDINGS_COUNT, current + 1).apply()
        unlockAchievement("ach_secret_ending")
    }

    fun getSecretEndingsCount(): Int = prefs.getInt(KEY_SECRET_ENDINGS_COUNT, 0)

    fun clearAllData() {
        prefs.edit().clear().apply()
    }
}
