package com.example.stockgazer.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = Primary900,
    primary = Primary100,
    primaryContainer = Primary800,
    surface = Primary700,
    secondary = Gain300,
    secondaryContainer = Gain500,
    tertiary = Loss300,
    tertiaryContainer = Loss500
)

private val LightColorScheme = lightColorScheme(
    background = Primary100,
    primary = Primary900,
    primaryContainer = Primary200,
    surface = Primary300,
    secondary = Gain700,
    secondaryContainer = Gain500,
    tertiary = Loss700,
    tertiaryContainer = Loss500
)

@Composable
fun StockGazerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MontserratTypography,
        content = content
    )
}