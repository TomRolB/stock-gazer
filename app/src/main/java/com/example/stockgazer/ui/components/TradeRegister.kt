package com.example.stockgazer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.stockgazer.ui.components.icons.StockAmountIcon
import com.example.stockgazer.ui.screens.chart.TradeType
import com.example.stockgazer.ui.theme.Gain300
import com.example.stockgazer.ui.theme.IconMedium
import com.example.stockgazer.ui.theme.IconSmall
import com.example.stockgazer.ui.theme.IconToTextSpacingMedium
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.NumbersHorizontalSpacing
import com.example.stockgazer.ui.theme.PaddingMedium
import com.example.stockgazer.ui.theme.PaddingSmall
import com.example.stockgazer.ui.theme.Primary100
import com.example.stockgazer.ui.theme.TradeRegisterElementSpacing
import com.example.stockgazer.util.asPercentageString

@Composable
fun TradeRegister(
    type: TradeType,
    amount: Int,
    date: String,
    time: String,
    price: Double,
    percentChange: Double,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(TradeRegisterElementSpacing),
        modifier = Modifier.padding(horizontal = PaddingMedium, vertical = PaddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StockCount(amount, type)
        TradeDateTime(date, time)
        PriceAndChange(price, percentChange, type)
    }
}

@Composable
private fun PriceAndChange(price: Double, percentChange: Double, tradeType: TradeType) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(NumbersHorizontalSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(price.toString(), fontWeight = FontWeight.Bold, color = Primary100)
        Text(
            percentChange.asPercentageString(),
            fontWeight = FontWeight.Bold,
            color = if (percentChange >= 0) Gain300 else Loss300
        )
    }
}

@Composable
private fun TradeDateTime(date: String, time: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(NumbersHorizontalSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(date, color = Primary100)
        Text(time, color = Primary100)
    }
}

@Composable
private fun StockCount(amount: Int, tradeType: TradeType) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(IconToTextSpacingMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StockAmountIcon(
            type = tradeType,
            size = IconMedium
        )
        Text(
            amount.toString(),
            color = if (tradeType == TradeType.Sell) Loss300 else Gain300,
            fontWeight = FontWeight.Bold
        )
    }
}
