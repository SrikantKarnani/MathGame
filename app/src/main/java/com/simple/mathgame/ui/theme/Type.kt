package com.simple.mathgame.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.simple.mathgame.R

// Set of Material typography styles to start with
val joseinfFonts = FontFamily(
    Font(R.font.josefin),
    Font(R.font.josefin_bold, weight = FontWeight.Bold),
    Font(R.font.josefin_semibold, weight = FontWeight.SemiBold),
    Font(R.font.josefin_thin, weight = FontWeight.Thin),
    Font(R.font.josefin_thinitalic, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(R.font.josefin_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.josefin_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.josefin_light, weight = FontWeight.Light),
)

val glutenFonts = FontFamily(
    Font(R.font.gluten_regular),
    Font(R.font.gluten_bold, weight = FontWeight.Bold),
    Font(R.font.gluten_semibold, weight = FontWeight.SemiBold),
    Font(R.font.gluten_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.gluten_medium, weight = FontWeight.Medium),
    Font(R.font.gluten_black, weight = FontWeight.Black),
    Font(R.font.gluten_thin, weight = FontWeight.Thin),
    Font(R.font.gluten_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.gluten_light, weight = FontWeight.Light),
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = glutenFonts,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = glutenFonts,
        fontWeight = FontWeight.Bold,
        fontSize = 64.sp
    ),
    h2 = TextStyle(
        fontFamily = glutenFonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 52.sp
    ),
    h3 = TextStyle(
        fontFamily = glutenFonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp
    ),
    h4 = TextStyle(
        fontFamily = glutenFonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp
    ),
    h5 = TextStyle(
        fontFamily = glutenFonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    h6 = TextStyle(
        fontFamily = glutenFonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = glutenFonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    )
    /* Other default text styles to override

    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)