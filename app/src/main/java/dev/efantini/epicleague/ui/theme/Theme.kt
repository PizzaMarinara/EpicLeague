package dev.efantini.epicleague.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Dark4,
    primaryVariant = Dark3,
    secondary = AccentDark1,
    secondaryVariant = AccentDark2,
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
    secondary = AccentLight1,
    secondaryVariant = AccentLight2,
    background = Light1,
    surface = Light3,
    onPrimary = Black,
    onSecondary = Black,
    onBackground = Black,
    onSurface = Black,
)

@Composable
fun EpicLeagueTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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
    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalCardShape provides CardShape(),
        LocalFontSize provides FontSize()

    ) {
        MaterialTheme(
            colors = colors,
            typography = EpicLeagueTypography,
            shapes = BasicShapes,
            content = content
        )
    }
}
