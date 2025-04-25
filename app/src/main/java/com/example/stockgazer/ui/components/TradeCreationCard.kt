package com.example.stockgazer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.icons.StockAmountIcon
import com.example.stockgazer.ui.components.input.DateField
import com.example.stockgazer.ui.components.input.TimeField
import com.example.stockgazer.ui.screens.chart.ChartViewModel
import com.example.stockgazer.ui.screens.chart.Trade
import com.example.stockgazer.ui.screens.chart.TradeType.Buy
import com.example.stockgazer.ui.screens.chart.TradeType.Sell
import com.example.stockgazer.ui.theme.CardBorderRadius
import com.example.stockgazer.ui.theme.ElementSpacing
import com.example.stockgazer.ui.theme.Gain300
import com.example.stockgazer.ui.theme.HeadlineToIconSpacing
import com.example.stockgazer.ui.theme.IconBig
import com.example.stockgazer.ui.theme.InputSpacing
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.PaddingMedium
import com.example.stockgazer.ui.theme.Primary100
import com.example.stockgazer.ui.theme.Primary200
import com.example.stockgazer.ui.theme.Primary800
import com.example.stockgazer.ui.theme.Primary900
import kotlin.math.absoluteValue

@Composable
fun TradeCreationCard() {
    val viewModel = hiltViewModel<ChartViewModel>()
    val currentTrade by viewModel.currentTrade.collectAsState()

    Card(
        colors = CardDefaults.cardColors(containerColor = Primary800),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CardBorderRadius),
    ) {
        Box(
            modifier = Modifier.padding(PaddingMedium)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(ElementSpacing)
            ) {
                TradeType(viewModel)
                TradeCreationFields(currentTrade, viewModel)
                TradeCreationButtons(viewModel)
            }
        }
    }
}

@Composable
private fun TradeCreationFields(currentTrade: Trade, viewModel: ChartViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(InputSpacing)
    ) {
        OutlinedTextField(
            label = {
                Text(stringResource(R.string.amount_traded_field_label), color = Primary100)
            },
            value = currentTrade.amount.absoluteValue.toString(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = {
                val input = it.toIntOrNull()
                if (input != null) viewModel.updateTradeAmount(input)
            }
        )

        OutlinedTextField(label = {
            Text(stringResource(R.string.price_field_label), color = Primary100)
        },
            value = currentTrade.price.absoluteValue.toString(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = {
                val input = it.toDoubleOrNull()
                if (input != null) viewModel.updateTradePrice(input)
            }
        )

        DateField(
            label = stringResource(R.string.trade_date_field_label),
            initialDate = currentTrade.date,
            onDateSelected = { viewModel.updateTradeDate(it) },
            modifier = Modifier.fillMaxWidth(),
            timezone = viewModel.zoneIdProvider.getTimeZone()
        )

        TimeField(
            label = stringResource(R.string.trade_time_field_label),
            initialTime = currentTrade.time,
            onTimeSelected = { viewModel.updateTradeTime(it) },
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
private fun TradeType(viewModel: ChartViewModel) {
    val currentTrade by viewModel.currentTrade.collectAsState()


    Row(horizontalArrangement = Arrangement.spacedBy(HeadlineToIconSpacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            viewModel.toggleTradeType()
        }) {
        SingleChoiceSegmentedButtonRow {
            SegmentedButton(
                colors = SegmentedButtonDefaults.colors().copy(activeContainerColor = Primary800),
                selected = currentTrade.type == Buy,
                onClick = { viewModel.toggleTradeType() },
                shape = SegmentedButtonDefaults.itemShape(
                    0, count = 2
                )
            ) {
                Text(
                    stringResource(R.string.buy),
                    color = Gain300,
                )
            }

            SegmentedButton(
                colors = SegmentedButtonDefaults.colors().copy(activeContainerColor = Primary800),
                selected = currentTrade.type == Sell,
                onClick = { viewModel.toggleTradeType() },
                shape = SegmentedButtonDefaults.itemShape(
                    1, count = 2
                )
            ) {
                Text(
                    stringResource(R.string.sell),
                    color = Loss300,
                )
            }
        }
    }
}

@Composable
private fun TradeCreationButtons(viewModel: ChartViewModel) {
    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { viewModel.submitTrade() }, colors = ButtonColors(
                containerColor = Primary100,
                contentColor = Primary900,
                disabledContainerColor = Primary200,
                disabledContentColor = Primary800
            )
        ) {
            Text(stringResource(R.string.trade_submit_button_text))
        }
    }
}

