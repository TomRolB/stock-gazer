package com.example.stockgazer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stockgazer.ui.components.images.Logo
import com.example.stockgazer.ui.theme.ElementSpacing
import com.example.stockgazer.ui.theme.Gain300
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.PrimaryDark
import com.example.stockgazer.ui.theme.PrimaryLight

@Composable
fun ActiveStockCard(
    isGain: Boolean,
    symbol: String,
    variation: Double,
    trades: Long,
    volume: Long,
    logoUrl: String? = null
) {
    Box(modifier = Modifier.border(
            width = 0.5.dp,
            color = PrimaryLight,
            shape = RoundedCornerShape(8.dp)
    ).height(32.dp)
    ) {
        Row(
            modifier = Modifier
                .background(PrimaryDark)
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(ElementSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Logo(
                logoUrl = logoUrl ?: "https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/MSFT.png",
                symbol = symbol
            )
            Column {
                Text(
                    symbol,
                    color = PrimaryLight,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "%$variation",
                    color = if (isGain) Gain300 else Loss300,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "%,d".format(trades),
                    textAlign = TextAlign.Right,
                    color = PrimaryLight,
                )
                Text(
                    "%,d".format(volume),
                    textAlign = TextAlign.Right,
                    color = PrimaryLight,
                )
            }
        }
    }
}