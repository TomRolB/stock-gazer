package com.example.stockgazer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stockgazer.ui.components.icons.StockAmountIcon
import com.example.stockgazer.ui.screens.chart.TradeType
import com.example.stockgazer.ui.theme.Gain300
import com.example.stockgazer.ui.theme.IconSmall
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.PaddingMedium
import com.example.stockgazer.ui.theme.PaddingSmall
import com.example.stockgazer.ui.theme.Primary100

@Composable
fun TradeRegister(
    type: TradeType,
    amount: Int,
    date: String,
    time: String,
    price: Double,
    variation: Double,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = PaddingMedium, vertical = PaddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StockCount(amount, type)
        TradeDateTime(date, time)
        PriceAndVariation(price, variation, type)
    }
}

@Composable
private fun PriceAndVariation(price: Double, variation: Double, tradeType: TradeType) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(price.toString(), fontWeight = FontWeight.Bold, color = Primary100)
        Text(
            "$variation%",
            fontWeight = FontWeight.Bold,
            color = if (tradeType == TradeType.Buy) Gain300 else Loss300
        )
    }
}

@Composable
private fun TradeDateTime(date: String, time: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(date, color = Primary100)
        Text(time, color = Primary100)
    }
}

@Composable
private fun StockCount(amount: Int, tradeType: TradeType) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StockAmountIcon(
            type = tradeType,
            size = IconSmall
        )
        Text(
            amount.toString(),
            color = if (amount < 0) Loss300 else Gain300,
            fontWeight = FontWeight.Bold
        )
    }
}
