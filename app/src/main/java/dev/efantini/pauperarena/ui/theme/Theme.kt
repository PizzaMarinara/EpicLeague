package dev.efantini.pauperarena.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Dark4,
    primaryVariant = Dark3,
    secondary = Dark2,
    background = Dark1,
    surface = Dark3,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = White,

)

private val LightColorPalette = lightColors(
    primary = Light4,
    primaryVariant = Light3,
    secondary = Light2,
    background = Light1,
    surface = Light3,
    onPrimary = Black,
    onSecondary = Black,
    onBackground = Black,
    onSurface = Black,
)

@Composable
fun PauperArenaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setSystemBarsColor(
            color = Dark3
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = Light3
        )
    }

    MaterialTheme(
        colors = colors,
        typography = PauperArenaTypography,
        shapes = Shapes,
        content = content
    )
}
