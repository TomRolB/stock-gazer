package com.example.stockgazer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.BorderWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.images.Logo
import com.example.stockgazer.ui.theme.ElementSpacing
import com.example.stockgazer.ui.theme.Gain300
import com.example.stockgazer.ui.theme.IconSmall
import com.example.stockgazer.ui.theme.IconToTextSpacingSmall
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.PaddingSmall
import com.example.stockgazer.ui.theme.Primary100
import com.example.stockgazer.ui.theme.Primary900
import com.example.stockgazer.ui.theme.StockCardBorderRadius
import com.example.stockgazer.util.asPercentageString

@Composable
fun ActiveStockCard(
    isGain: Boolean,
    symbol: String,
    variation: Double,
    trades: Long,
    volume: Long,
    logoUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                width = BorderWidth,
                color = Primary100,
                shape = RoundedCornerShape(StockCardBorderRadius)
            )
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .background(Primary900)
                .padding(all = PaddingSmall),
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
                    variation.asPercentageString(),
                    color = if (isGain) Gain300 else Loss300,
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(horizontalAlignment = Alignment.Start) {
                Trades(trades)
                Volume(volume)
            }
        }
    }
}

@Composable
private fun Trades(trades: Long) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(IconToTextSpacingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.trades
            ),
            contentDescription = stringResource(R.string.trade_icon_content_description),
            tint = Primary100,
            modifier = Modifier.size(IconSmall)
        )

        Text(
            "%,d".format(trades),
            textAlign = TextAlign.Left,
            color = Primary100,
        )
    }
}

@Composable
private fun Volume(volume: Long) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(IconToTextSpacingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.volume
            ),
            contentDescription = stringResource(R.string.volume_icon_content_description),
            tint = Primary100,
            modifier = Modifier.size(size = IconSmall)
        )

        Text(
            "%,d".format(volume),
            textAlign = TextAlign.Left,
            color = Primary100,
        )
    }
}