package com.example.stockgazer.ui.components.charts

import androidx.compose.ui.graphics.toArgb
import com.example.stockgazer.ui.theme.CandleThickness
import com.example.stockgazer.ui.theme.Gain500
import com.example.stockgazer.ui.theme.Loss500
import com.example.stockgazer.ui.theme.Primary100
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.layer.CandlestickCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.layer.CandlestickCartesianLayer.Candle
import com.patrykandpatrick.vico.core.cartesian.layer.absolute
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import kotlin.math.ceil
import kotlin.math.floor

val RangeProvider =
    object : CartesianLayerRangeProvider {
        override fun getMinY(minY: Double, maxY: Double, extraStore: ExtraStore) =
            Y_STEP * floor(minY / Y_STEP)

        override fun getMaxY(minY: Double, maxY: Double, extraStore: ExtraStore) =
            Y_STEP * ceil(maxY / Y_STEP)
    }

val CandleProvider = CandlestickCartesianLayer.CandleProvider.absolute(
    bullish = Candle(body = LineComponent(
        fill = Fill(Gain500.toArgb()),
        thicknessDp = CandleThickness
    )
    ),
    neutral = Candle(body = LineComponent(
        fill = Fill(Primary100.toArgb()),
        thicknessDp = CandleThickness
    )
    ),
    bearish = Candle(body = LineComponent(
        fill = Fill(Loss500.toArgb()),
        thicknessDp = CandleThickness
    )
    )
)