package com.example.stockgazer.ui.components.charts

import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

val StartAxisValueFormatter = CartesianValueFormatter.decimal(DecimalFormat("$#,###.##"))

val BottomAxisValueFormatter =
    object : CartesianValueFormatter {
        private val dateFormat =
            SimpleDateFormat("h a", Locale.US).apply { timeZone = TimeZone.getTimeZone("GMT") }

        override fun format(
            context: CartesianMeasuringContext,
            value: Double,
            verticalAxisPosition: Axis.Position.Vertical?,
        ) = dateFormat.format(value * MS_IN_H)
    }

val MarkerValueFormatter =
    DefaultCartesianMarker.ValueFormatter.default(DecimalFormat("$#,###.00"))