package com.simple.mathgame.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(8.dp)
)

val OperationRoundedShapes = Shapes(
    small = RoundedCornerShape(24.dp, 0.dp, 0.dp, 24.dp),
    medium = RoundedCornerShape(36.dp, 0.dp, 0.dp, 36.dp),
    large = RoundedCornerShape(48.dp, 0.dp, 0.dp, 48.dp),
)