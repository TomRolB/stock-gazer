package com.example.stockgazer.ui.components.charts

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockgazer.ui.screens.chart.ChartViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEnd
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberCandlestickCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.candlestickSeries
import com.patrykandpatrick.vico.core.common.component.TextComponent


@Composable
fun CandlestickChart(modifier: Modifier = Modifier) {
    val modelProducer = cartesianChartModelProducer()
    val viewModel = hiltViewModel<ChartViewModel>()
    val bars by viewModel.bars.collectAsState()

    if (!bars.isEmpty()) {
        LaunchedEffect(Unit) {
            modelProducer.runTransaction {
                candlestickSeries(
                    (0..<bars.opening.size).toList(),
                    bars.opening,
                    bars.closing,
                    bars.low,
                    bars.high
                )
            }
        }


        CartesianChartHost(
            rememberCartesianChart(
                rememberCandlestickCartesianLayer(
                    rangeProvider = RangeProvider,
                    candleProvider = getCandleProvider(MaterialTheme.colorScheme)
                ),
                endAxis = VerticalAxis.rememberEnd(
                    label = TextComponent(color = MaterialTheme.colorScheme.primary.toArgb()),
                    valueFormatter = StartAxisValueFormatter,
                    itemPlacer = SteppedItemPlacer,
                ),
                bottomAxis = HorizontalAxis.rememberBottom(
                    label = TextComponent(color = MaterialTheme.colorScheme.primary.toArgb()),
                    guideline = null,
                    valueFormatter = BottomAxisValueFormatter(
                        bars.timestamps,
                        viewModel.zoneIdProvider.getTimeZone()
                    )
                ),
            ),
            modelProducer = modelProducer,
            modifier = modifier
        )
    }
}

@Composable
private fun cartesianChartModelProducer() = CartesianChartModelProducer()

// Could not create a Preview, since the viewModel is inside this widget.
// Also tried to pass the bars as an argument, but this caused the CartesianChartHost
//  to re-render many times, which breaks the chart: the Vico library asks you
//  to use `modelProducer.runTransaction` to update the data, NOT to re-render the chart.