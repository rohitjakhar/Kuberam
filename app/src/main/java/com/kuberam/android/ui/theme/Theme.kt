package com.kuberam.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xff303457),
    primaryVariant = Purple700,
    secondary = Color(0xff3deb94),
    background = Color(0xff203147),
    onSurface = Color(0xff303457),
    secondaryVariant = Color(0xff283488),
    error = Color(0xfff12e0b)
)

private val LightColorPalette = lightColors(
    primary = Color(0xff1B98F5),
    primaryVariant = Purple700,
    onSurface = Color(0xffffd85a),
    secondaryVariant = Color(0xffffd85a),
    error = Color(0xffe41e13)

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun KuberamTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
