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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.input.DateField
import com.example.stockgazer.ui.components.input.TimeField
import com.example.stockgazer.ui.screens.chart.ChartViewModel
import com.example.stockgazer.ui.screens.chart.TradeType.Buy
import com.example.stockgazer.ui.screens.chart.TradeType.Sell
import com.example.stockgazer.ui.theme.CardBorderRadius
import com.example.stockgazer.ui.theme.ElementSpacing
import com.example.stockgazer.ui.theme.HeadlineToIconSpacing
import com.example.stockgazer.ui.theme.InputSpacing
import com.example.stockgazer.ui.theme.PaddingMedium
import com.example.stockgazer.ui.theme.Primary300
import com.example.stockgazer.ui.theme.Primary700

@Composable
fun TradeCreationCard() {
    val viewModel = hiltViewModel<ChartViewModel>()

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
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
                TradeCreationFields()
                TradeCreationButtons(viewModel)
            }
        }
    }
}

@Composable
private fun TradeCreationFields() {
    val viewModel = hiltViewModel<ChartViewModel>()
    val currentTrade by viewModel.currentTrade.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(InputSpacing)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(ElementSpacing)
        ) {
            OutlinedTextField(
                label = {
                    Text(stringResource(R.string.amount_traded_field_label), color = MaterialTheme.colorScheme.primary)
                },
                value = currentTrade.amount,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = {
                    viewModel.updateTradeAmount(it)
                },
                textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                label = {
                    Text(stringResource(R.string.price_field_label), color = MaterialTheme.colorScheme.primary)
                },
                value = currentTrade.price,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = {
                    viewModel.updateTradePrice(it)
                },
                textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.weight(2f)
            )
        }


        DateField(
            label = stringResource(R.string.trade_date_field_label),
            initialDate = currentTrade.date,
            onDateSelected = { viewModel.updateTradeDate(it) },
            modifier = Modifier.fillMaxWidth(),
            timezone = viewModel.zoneIdProvider.getTimeZone(),
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
            val colors = SegmentedButtonDefaults.colors().copy(
                activeContainerColor = MaterialTheme.colorScheme.surface,
                inactiveContainerColor = MaterialTheme.colorScheme.primaryContainer,
            )
            SegmentedButton(
                colors = colors.copy(activeContentColor = MaterialTheme.colorScheme.secondary),
                selected = currentTrade.type == Buy,
                onClick = { viewModel.toggleTradeType() },
                shape = SegmentedButtonDefaults.itemShape(
                    0, count = 2
                )
            ) {
                Text(
                    stringResource(R.string.buy),
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

            SegmentedButton(
                colors = colors.copy(activeContentColor = MaterialTheme.colorScheme.tertiaryContainer),
                selected = currentTrade.type == Sell,
                onClick = { viewModel.toggleTradeType() },
                shape = SegmentedButtonDefaults.itemShape(
                    1, count = 2
                )
            ) {
                Text(
                    stringResource(R.string.sell),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                )
            }
        }
    }
}

@Composable
private fun TradeCreationButtons(viewModel: ChartViewModel) {
    val currentTrade by viewModel.currentTrade.collectAsState()

    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
        val amount = currentTrade.amount.toIntOrNull()
        val price = currentTrade.price.toDoubleOrNull()
        Button(
            enabled = amount != null &&
                    amount > 0 &&
                    price != null &&
                    price > 0,
            onClick = { viewModel.submitTrade() }, colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = Primary300,
                disabledContentColor = Primary700
            )
        ) {
            Text(stringResource(R.string.trade_submit_button_text))
        }
    }
}
