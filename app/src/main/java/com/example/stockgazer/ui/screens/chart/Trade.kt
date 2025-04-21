package com.example.stockgazer.ui.screens.chart

import com.example.stockgazer.ui.screens.chart.TradeType.Buy
import com.example.stockgazer.ui.screens.chart.TradeType.Sell
import java.time.LocalDate
import java.time.LocalTime

data class Trade(
    val type: TradeType = Buy,
    val amount: Int = 1,
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val price: Double = 0.0,
)

enum class TradeType {
    Buy,
    Sell
}

operator fun TradeType.not() = when (this) {
    Buy -> Sell
    Sell -> Buy
}
