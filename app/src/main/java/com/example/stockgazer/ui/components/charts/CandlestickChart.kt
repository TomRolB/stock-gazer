package com.example.stockgazer.ui.components.charts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.stockgazer.data.repository.FakeBarRepository
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
    val barRepository = FakeBarRepository()
    val bars = barRepository.getBarsForSymbol("MSFT")

    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            candlestickSeries(x, bars.opening, bars.closing, bars.low, bars.high)
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
                guideline = null, valueFormatter = BottomAxisValueFormatter
            ),
//            marker = rememberMarker(valueFormatter = MarkerValueFormatter, showIndicator = false),
        ),
        modelProducer = modelProducer,
        modifier = modifier
    )
}

@Composable
private fun cartesianChartModelProducer() = CartesianChartModelProducer()