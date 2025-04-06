package com.example.stockgazer.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.stockgazer.ui.theme.Primary100

@Composable
fun Headline(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.headlineLarge,
        color = Primary100,
        fontWeight = FontWeight.Bold
    )
}