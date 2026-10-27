package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DreamyLightColorScheme = lightColorScheme(
    primary = PastelPinkPrimary,
    onPrimary = Color.White,
    primaryContainer = PastelPinkContainer,
    onPrimaryContainer = PastelPinkDark,
    secondary = PastelLavenderDark,
    onSecondary = Color.White,
    secondaryContainer = PastelLavenderContainer,
    onSecondaryContainer = SoftPurple,
    tertiary = AccentGold,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFF3E0),
    onTertiaryContainer = Color(0xFFE65100),
    background = SoftBlushCream,
    onBackground = TextPrimaryDark,
    surface = SoftCardSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = PastelLavenderLight,
    onSurfaceVariant = TextSecondary,
    outline = BorderPastel,
    outlineVariant = BorderLight
)

private val DreamyDarkColorScheme = darkColorScheme(
    primary = PastelPinkPrimary,
    onPrimary = Color(0xFF381E27),
    primaryContainer = Color(0xFF5A2A38),
    onPrimaryContainer = PastelPinkLight,
    secondary = PastelLavender,
    onSecondary = Color(0xFF2E243D),
    secondaryContainer = Color(0xFF453859),
    onSecondaryContainer = PastelLavenderLight,
    tertiary = AccentGold,
    onTertiary = Color(0xFF3E2700),
    background = Color(0xFF19161F),
    onBackground = Color(0xFFECE4F0),
    surface = Color(0xFF221E2C),
    onSurface = Color(0xFFECE4F0),
    surfaceVariant = Color(0xFF2E273A),
    onSurfaceVariant = Color(0xFFD4C8E0),
    outline = Color(0xFF4F465C),
    outlineVariant = Color(0xFF393244)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep intentional dreamy pastel theme by default
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DreamyDarkColorScheme else DreamyLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
