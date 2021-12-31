package dev.efantini.pauperarena.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Yellow80,
    primaryVariant = Red80,
    secondary = Blue80
)

private val LightColorPalette = lightColors(
    primary = Yellow40,
    primaryVariant = Red40,
    secondary = Blue40
)

@Composable
fun PauperArenaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = PauperArenaTypography,
        shapes = Shapes,
        content = content
    )
}
