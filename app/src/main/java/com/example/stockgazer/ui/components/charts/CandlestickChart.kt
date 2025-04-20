package com.example.stockgazer.ui.components.charts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockgazer.R
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


@Composable
fun CandlestickChart(modifier: Modifier = Modifier) {
    val modelProducer = cartesianChartModelProducer()
    val viewModel = hiltViewModel<ChartViewModel>()
    val bars by viewModel.bars.collectAsState()

    // TODO: change. Too much indentation
    if (!bars.isEmpty()) {

        LaunchedEffect(Unit) {
            modelProducer.runTransaction {
                candlestickSeries((0..<bars.opening.size).toList(), bars.opening, bars.closing, bars.low, bars.high)
            }
        }

        CartesianChartHost(
            rememberCartesianChart(
                rememberCandlestickCartesianLayer(
                    rangeProvider = RangeProvider,
                    candleProvider = CandleProvider
                ),
                endAxis = VerticalAxis.rememberEnd(
                    valueFormatter = StartAxisValueFormatter,
                    itemPlacer = SteppedAndLastItemPlacer(bars.closing),
                ),
                bottomAxis = HorizontalAxis.rememberBottom(
                    guideline = null,
                    valueFormatter = BottomAxisValueFormatter(
                        bars.timestamps,
                        stringResource(R.string.time_zone)
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