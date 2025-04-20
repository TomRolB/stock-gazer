package com.example.stockgazer.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.screens.chart.ChartViewModel
import com.example.stockgazer.ui.theme.Primary100

@Composable
fun YourTradesSection() {
    val viewModel = hiltViewModel<ChartViewModel>()
    val isAddingTrade = viewModel.isAddingTrade.collectAsState()
    val rotationAngle by animateFloatAsState(
        targetValue = if (isAddingTrade.value) 45f else 0f,
        label = "Icon Rotation"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Headline(stringResource(R.string.your_trades_title))
        IconButton(
            onClick = {
                viewModel.toggleAddingTrade()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "",
                tint = Primary100,
                modifier = Modifier.rotate(rotationAngle)
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    AnimatedVisibility(
        visible = isAddingTrade.value
    ) {
        TradeCreationCard()
    }
    Spacer(modifier = Modifier.height(16.dp))

    repeat(10) {
        TradeRegister(1, "01/01/2024", "13:43", 5600.0, 0.6)
    }
}