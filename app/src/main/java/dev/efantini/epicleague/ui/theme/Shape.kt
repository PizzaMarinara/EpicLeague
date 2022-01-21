package dev.efantini.epicleague.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

val BasicShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

data class CardShape(
    val default: RoundedCornerShape = RoundedCornerShape(15.dp),
    val extraSmall: RoundedCornerShape = RoundedCornerShape(5.dp),
    val small: RoundedCornerShape = RoundedCornerShape(10.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(15.dp),
    val large: RoundedCornerShape = RoundedCornerShape(20.dp),
    val extraLarge: RoundedCornerShape = RoundedCornerShape(25.dp)
)

val LocalCardShape = compositionLocalOf { CardShape() }
