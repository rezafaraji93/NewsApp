package com.faraji.newsapp.core.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = OrangeAccent,
    background = DarkGray,
    onBackground = TextWhite,
    onPrimary = DarkGray,
    surface = MediumGray,
    onSurface = LightGray,
)

@Composable
fun NewsAppTheme(content: @Composable() () -> Unit) {

    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}