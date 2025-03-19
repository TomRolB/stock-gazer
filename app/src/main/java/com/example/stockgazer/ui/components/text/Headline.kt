package com.example.stockgazer.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.stockgazer.ui.theme.PrimaryLight

@Composable
fun Headline(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.headlineLarge,
        color = PrimaryLight,
        fontWeight = FontWeight.Bold
    )
}