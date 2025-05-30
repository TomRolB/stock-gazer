package com.example.stockgazer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.stockgazer.R

val MontserratFontFamily = FontFamily(
    Font(R.font.montserrat_regular, weight = FontWeight.Normal),
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(R.font.montserrat_extra_bold, weight = FontWeight.ExtraBold),
)

val MontserratTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = MontserratFontFamily,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.5,
        letterSpacing = 0.5.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = MontserratFontFamily,
        fontSize = 30.sp,
        lineHeight = 30.sp * 1.1,
        letterSpacing = 0.sp
    ),
)