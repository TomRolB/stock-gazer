package com.example.stockgazer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stockgazer.ui.theme.Gain300
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.Primary100

@Composable
fun TradeRegister(
    amount: Int,
    date: String,
    time: String,
    price: Double,
    variation: Double,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            amount.toString(),
            color = if (amount < 0) Loss300 else Gain300,
            fontWeight = FontWeight.Bold
        );
        Text(date, color = Primary100)
        Text(time, color = Primary100)
        Text(price.toString(), fontWeight = FontWeight.Bold, color = Primary100)
        Text("$variation%", fontWeight = FontWeight.Bold, color = if (variation < 0) Loss300 else Gain300)
    }
}