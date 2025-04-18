package com.example.stockgazer.data.response

import com.example.stockgazer.domain.model.Stock

data class TopMarketMoversResponse(
    val gainers: List<Stock> = listOf(),
    val losers: List<Stock> = listOf(),
)

data class BarsResponse(
    val opening: List<Double> = listOf(),
    val closing: List<Double> = listOf(),
    val low: List<Double> = listOf(),
    val high: List<Double> = listOf(),
) {
    fun isEmpty(): Boolean {
        return listOf(opening, closing, low, high).any { it.isEmpty() }
    }
}