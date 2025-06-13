package com.example.stockgazer.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.stockgazer.R
import com.example.stockgazer.ui.theme.StockGazerTheme

@Composable
fun Display(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
    )
}

@Composable
@Preview(showBackground = false)
fun DisplayPreview() {
    StockGazerTheme {
        Display(stringResource(R.string.follow_list_title))
    }
}
