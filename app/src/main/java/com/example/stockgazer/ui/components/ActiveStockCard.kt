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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.BorderWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.images.Logo
import com.example.stockgazer.ui.theme.ElementSpacing
import com.example.stockgazer.ui.theme.IconSmall
import com.example.stockgazer.ui.theme.IconToTextSpacingSmall
import com.example.stockgazer.ui.theme.PaddingSmall
import com.example.stockgazer.ui.theme.Primary900
import com.example.stockgazer.ui.theme.StockCardBorderRadius
import com.example.stockgazer.ui.theme.StockGazerTheme
import com.example.stockgazer.util.asPercentageString

@Composable
fun ActiveStockCard(
    isGain: Boolean,
    symbol: String,
    variation: Double,
    trades: Long,
    volume: Long,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                width = BorderWidth,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(StockCardBorderRadius)
            )
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(all = PaddingSmall),
            horizontalArrangement = Arrangement.spacedBy(ElementSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val logoUrl = stringResource(R.string.logos_url) + "$symbol.png"
            Logo(
                logoUrl = logoUrl,
                symbol = symbol
            )
            Column {
                Text(
                    symbol,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    variation.asPercentageString(),
                    color = if (isGain) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiaryContainer,
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
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(IconSmall)
        )

        Text(
            "%,d".format(trades),
            textAlign = TextAlign.Left,
            color = MaterialTheme.colorScheme.primary,
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
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(size = IconSmall)
        )

        Text(
            "%,d".format(volume),
            textAlign = TextAlign.Left,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
@Preview(showBackground = false)
fun ActiveStockCardPreview() {
    StockGazerTheme {
        ActiveStockCard(
            isGain = true,
            symbol = "GOOG",
            variation = 2.75,
            trades = 123_456,
            volume = 9_876_543
        )
    }
}

