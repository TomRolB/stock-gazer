package com.example.stockgazer.ui.components.charts

import androidx.compose.ui.graphics.toArgb
import androidx.compose.material3.ColorScheme
import com.example.stockgazer.ui.theme.CandleThickness
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

fun getCandleProvider(colors: ColorScheme): CandlestickCartesianLayer.CandleProvider =
    CandlestickCartesianLayer.CandleProvider.absolute(
        bullish = Candle(body = LineComponent(
            fill = Fill(colors.secondaryContainer.toArgb()),
            thicknessDp = CandleThickness
        )
        ),
        neutral = Candle(body = LineComponent(
            fill = Fill(colors.primary.toArgb()),
            thicknessDp = CandleThickness
        )
        ),
        bearish = Candle(body = LineComponent(
            fill = Fill(colors.tertiaryContainer.toArgb()),
            thicknessDp = CandleThickness
        )
        )
    )
