package com.example.stockgazer.ui.components.charts

import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import java.text.DecimalFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

val StartAxisValueFormatter = CartesianValueFormatter.decimal(DecimalFormat("$#,###.##"))

class BottomAxisValueFormatter(
    private val timestamps: List<Instant>,
    private val timeZone: ZoneId
) : CartesianValueFormatter {

    private val hourFormatter = DateTimeFormatter
        .ofPattern("h a", Locale.US)
        .withZone(timeZone)

    override fun format(
        context: CartesianMeasuringContext,
        value: Double,
        verticalAxisPosition: Axis.Position.Vertical?,
    ): CharSequence {
        val idx = value.toInt().coerceIn(timestamps.indices)
        val instant = timestamps[idx]
        val zdt = instant.atZone(timeZone)
        val today = zdt.dayOfWeek

        val showDayLabel = isFirstLabelOrDayChange(idx, today)

        val formattedHour = hourFormatter.format(instant)
        return if (showDayLabel) {
            today.getDisplayName(TextStyle.SHORT, Locale.US) + " " + formattedHour
        } else {
            formattedHour
        }
    }

    private fun isFirstLabelOrDayChange(idx: Int, today: DayOfWeek?) = idx == 0 || run {
        val prevInstant = timestamps[idx - 1]
        val prevDay = prevInstant.atZone(timeZone).dayOfWeek
        today != prevDay
    }
}

val MarkerValueFormatter =
    DefaultCartesianMarker.ValueFormatter.default(DecimalFormat("$#,###.00"))