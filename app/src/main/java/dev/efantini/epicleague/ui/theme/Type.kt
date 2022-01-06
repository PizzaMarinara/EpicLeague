package dev.efantini.epicleague.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.efantini.epicleague.R

val ProximaNovaFontFamily = FontFamily(
    Font(R.font.proximanova_thin, FontWeight.Light),
    Font(R.font.proximanova, FontWeight.Normal),
    Font(R.font.proximanova_bold, FontWeight.Bold)
)

val KarlaFontFamily = FontFamily(
    Font(R.font.karla_regular),
    Font(R.font.karla_bold, FontWeight.Bold)
)

val EpicLeagueTypography = Typography(
    body1 = TextStyle(
        fontFamily = KarlaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = KarlaFontFamily
    )
)
