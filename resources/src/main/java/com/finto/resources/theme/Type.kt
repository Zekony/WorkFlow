package com.finto.resources.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.finto.resources.R

val pilat_font_family = FontFamily(
    listOf(
        Font(R.font.pilat_extended_bold, FontWeight.Bold),
        Font(R.font.pilat_extended_semibold, FontWeight.SemiBold),
        Font(R.font.pilat_extended_regular, FontWeight.Normal)
    )
)

val inter_font_family = FontFamily(
    listOf(
        Font(R.font.inter_light, FontWeight.Light),
        Font(R.font.inter_medium, FontWeight.Medium)
    )
)


val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = pilat_font_family,
        fontWeight = FontWeight.SemiBold,
        fontSize = 53.sp,
        lineHeight = 50.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = inter_font_family,
        fontWeight = FontWeight.SemiBold,
        fontSize = 40.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = inter_font_family,
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = inter_font_family,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = inter_font_family,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = pilat_font_family,
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp
    ),
    titleMedium = TextStyle(
        fontFamily = inter_font_family,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = pilat_font_family,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = inter_font_family,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    ),

    )