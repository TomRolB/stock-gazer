package com.example.stockgazer.ui.components.charts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.stockgazer.R
import com.example.stockgazer.ui.screens.chart.BarPeriod
import com.example.stockgazer.ui.theme.PaddingMedium
import com.example.stockgazer.ui.theme.StockGazerTheme
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEnd
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberCandlestickCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.candlestickSeries
import java.time.Instant
import java.time.ZoneId


@Composable
fun CandlestickChart(bars: BarPeriod, timeZone: ZoneId, modifier: Modifier = Modifier) {
    val modelProducer = cartesianChartModelProducer()

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
                    rangeProvider = RangeProvider, candleProvider = CandleProvider
                ),
                endAxis = VerticalAxis.rememberEnd(
                    valueFormatter = StartAxisValueFormatter,
                    itemPlacer = SteppedItemPlacer,
                ),
                bottomAxis = HorizontalAxis.rememberBottom(
                    guideline = null, valueFormatter = BottomAxisValueFormatter(
                        bars.timestamps, timeZone
                    )
                ),
            ), modelProducer = modelProducer, modifier = modifier
        )
    }
}

@Composable
private fun cartesianChartModelProducer() = CartesianChartModelProducer()

@Composable
@Preview
fun CandlestickChartPreview() {
    val zoneString = stringResource(R.string.time_zone)
    val barPeriod = BarPeriod(
        timestamps = listOf(
            "2025-04-21T08:00:00Z", "2025-04-21T10:00:00Z", "2025-04-21T11:00:00Z", "2025-04-21T12:00:00Z",
            "2025-04-21T13:00:00Z", "2025-04-21T14:00:00Z", "2025-04-21T15:00:00Z", "2025-04-21T16:00:00Z",
            "2025-04-21T17:00:00Z", "2025-04-21T18:00:00Z", "2025-04-21T19:00:00Z", "2025-04-21T20:00:00Z",
            "2025-04-21T21:00:00Z", "2025-04-21T22:00:00Z", "2025-04-21T23:00:00Z", "2025-04-22T08:00:00Z",
            "2025-04-22T09:00:00Z", "2025-04-22T10:00:00Z", "2025-04-22T11:00:00Z", "2025-04-22T12:00:00Z"
        ).map { Instant.parse(it) },
        opening = listOf(
            363.9, 364.1, 365.73, 366.0, 364.49, 361.01, 360.0925, 358.235, 358.82, 357.38,
            355.9575, 359.17, 358.77, 359.1, 358.9, 362.46, 363.54, 362.28, 362.07, 362.2
        ),
        closing = listOf(
            364.1, 365.5, 363.94, 364.13, 361.03, 360.085, 358.23, 358.8, 357.3872, 355.97,
            359.14, 358.9, 359.02, 359.0, 361.12, 363.72, 362.0, 362.34, 361.0, 362.3
        ),
        low = listOf(
            363.9, 364.1, 363.4, 363.5, 361.02, 359.56, 357.75, 357.9501, 357.27, 355.67,
            355.86, 358.5073, 358.77, 358.55, 358.9, 362.4, 361.93, 361.97, 361.0, 359.12
        ),
        high = listOf(
            365.72, 365.5, 366.5, 369.067, 364.49, 361.53, 361.33, 359.69, 359.33, 357.84,
            359.75, 359.6, 359.25, 359.12, 361.12, 363.87, 363.54, 362.34, 362.3, 363.7
        )
    )

    StockGazerTheme {
        Box(Modifier.padding(PaddingMedium).fillMaxHeight()) {
            // TODO: fix
            CandlestickChart(
                bars = barPeriod,
                timeZone = ZoneId.of(zoneString)
            )
        }
    }
}