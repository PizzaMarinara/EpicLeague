package dev.efantini.epicleague.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class FontSize(
    val default: TextUnit = 16.sp,
    val extraSmall: TextUnit = 8.sp,
    val small: TextUnit = 12.sp,
    val medium: TextUnit = 16.sp,
    val large: TextUnit = 20.sp,
    val extraLarge: TextUnit = 24.sp
)

val LocalFontSize = compositionLocalOf { FontSize() }
