package com.example.stockgazer.ui.components.charts

import com.patrykandpatrick.vico.core.cartesian.CartesianDrawingContext
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.common.Position

private val SteppedItemPlacer = VerticalAxis.ItemPlacer.step({ Y_STEP })

class SteppedAndLastItemPlacer(val closing: List<Double>) : VerticalAxis.ItemPlacer {
        override fun getBottomLayerMargin(
            context: CartesianMeasuringContext,
            verticalLabelPosition: Position.Vertical,
            maxLabelHeight: Float,
            maxLineThickness: Float
        ): Float =
            SteppedItemPlacer.getBottomLayerMargin(
                context,
                verticalLabelPosition,
                maxLabelHeight,
                maxLineThickness
            )

        override fun getHeightMeasurementLabelValues(
            context: CartesianMeasuringContext,
            position: Axis.Position.Vertical
        ): List<Double> =
            SteppedItemPlacer.getHeightMeasurementLabelValues(context, position)

        override fun getLabelValues(
            context: CartesianDrawingContext,
            axisHeight: Float,
            maxLabelHeight: Float,
            position: Axis.Position.Vertical
        ): List<Double> {
            val steppedMeasurements = SteppedItemPlacer.getLabelValues(
                context,
                axisHeight,
                maxLabelHeight,
                position
            )
            return steppedMeasurements
                .toMutableList()
                .also { it.add(closing.last()) }
        }

        override fun getTopLayerMargin(
            context: CartesianMeasuringContext,
            verticalLabelPosition: Position.Vertical,
            maxLabelHeight: Float,
            maxLineThickness: Float
        ): Float =
            SteppedItemPlacer.getTopLayerMargin(
                context,
                verticalLabelPosition,
                maxLabelHeight,
                maxLineThickness
            )

        override fun getWidthMeasurementLabelValues(
            context: CartesianMeasuringContext,
            axisHeight: Float,
            maxLabelHeight: Float,
            position: Axis.Position.Vertical
        ): List<Double> =
            SteppedItemPlacer.getWidthMeasurementLabelValues(
                context,
                axisHeight,
                maxLabelHeight,
                position
            )
}