package com.example.stockgazer.ui.screens.chart

import com.example.stockgazer.data.response.BarsResponse
import java.time.Instant

data class BarPeriod(
    val timestamps: List<Instant> = emptyList(),
    val opening: List<Double> = emptyList(),
    val closing: List<Double> = emptyList(),
    val low: List<Double> = emptyList(),
    val high: List<Double> = emptyList(),
) {
    fun isEmpty(): Boolean {
        return listOf(opening, closing, low, high).any { it.isEmpty() }
    }

    companion object {
        fun fromBarResponse(barsResponse: BarsResponse): BarPeriod {
            val timestamps = ArrayList<Instant>()
            val opening = ArrayList<Double>()
            val closing = ArrayList<Double>()
            val low = ArrayList<Double>()
            val high = ArrayList<Double>()

            for (bar in barsResponse.bars) {
                timestamps.add(Instant.parse(bar.t))
                opening.add(bar.o)
                closing.add(bar.c)
                low.add(bar.l)
                high.add(bar.h)
            }

            return BarPeriod(timestamps, opening, closing, low, high)
        }
    }
}