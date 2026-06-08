package com.example.cityexplorer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF006D5B),
    primaryContainer = Color(0xFF7BF9DF),
    secondary = Color(0xFF4E635C),
    background = Color(0xFFFBFDFB),
    surface = Color(0xFFFBFDFB)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF5DDCB8),
    primaryContainer = Color(0xFF005242),
    background = Color(0xFF191C1B),
    surface = Color(0xFF191C1B)
)

@Composable
fun CityExplorerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography(),
        content = content
    )
}