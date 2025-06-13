package com.example.stockgazer.ui.components.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.stockgazer.R
import com.example.stockgazer.ui.screens.chart.TradeType

@Composable
fun StockAmountIcon(
    type: TradeType,
    size: Dp
) {
    if (type == TradeType.Buy) {
        Icon(
            painter = painterResource(id = R.drawable.buy),
            contentDescription = stringResource(R.string.green_arrow_pointing_upwards),
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(size)
        )
    } else {
        Icon(
            painter = painterResource(id = R.drawable.sell),
            contentDescription = stringResource(R.string.red_arrow_pointing_downwards),
            tint = MaterialTheme.colorScheme.tertiaryContainer,
            modifier = Modifier.size(size)
        )
    }
}
