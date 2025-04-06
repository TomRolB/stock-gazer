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
import com.example.stockgazer.ui.theme.Primary900
import com.example.stockgazer.ui.theme.Primary100

@Composable
fun ActiveStockCard(
    isGain: Boolean,
    symbol: String,
    variation: Double,
    trades: Long,
    volume: Long,
    logoUrl: String,
    modifier: Modifier? = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                width = 0.5.dp,
                color = Primary100,
                shape = RoundedCornerShape(8.dp)
            )
            .height(32.dp)
            .let { modifier?.let { outerModifier -> it.then(outerModifier) } ?: it }
    ) {
        Row(
            modifier = Modifier
                .background(Primary900)
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(ElementSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Logo(
                logoUrl = logoUrl,
                symbol = symbol
            )
            Column {
                Text(
                    symbol,
                    color = Primary100,
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
                    color = Primary100,
                )
                Text(
                    "%,d".format(volume),
                    textAlign = TextAlign.Right,
                    color = Primary100,
                )
            }
        }
    }
}