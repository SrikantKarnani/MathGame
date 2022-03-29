package com.simple.mathgame.ui.theme

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val BackgroundDark = Color(0xFF000000)

fun generateRandomLightGradientColor(): Color {
    // This is the base color which will be mixed with the generated one
    val baseColor: Int = android.graphics.Color.WHITE
    val baseRed: Int = android.graphics.Color.red(baseColor)
    val baseGreen: Int = android.graphics.Color.green(baseColor)
    val baseBlue: Int = android.graphics.Color.blue(baseColor)
    val red: Int = (baseRed + Random.nextInt(256)) / 2
    val green: Int = (baseGreen + Random.nextInt(256)) / 2
    val blue: Int = (baseBlue + Random.nextInt(256)) / 2
    return Color(red, green, blue)
}

fun generateRandomGradientColor(isDark : Boolean) : Color {
    return if(!isDark)
        generateRandomDarkGradientColor()
    else
        generateRandomLightGradientColor()
}

fun generateRandomDarkGradientColor(): Color {
    // This is the base color which will be mixed with the generated one
    val baseColor: Int = android.graphics.Color.BLACK
    val baseRed: Int = android.graphics.Color.red(baseColor)
    val baseGreen: Int = android.graphics.Color.green(baseColor)
    val baseBlue: Int = android.graphics.Color.blue(baseColor)
    val red: Int = (baseRed - (256 - Random.nextInt(256)) / 2)
    val green: Int = (baseGreen - (256 - Random.nextInt(256)) / 2)
    val blue: Int = (baseBlue - (256 - Random.nextInt(256)) / 2)
    return Color(red, green, blue)
}