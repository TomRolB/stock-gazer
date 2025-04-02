package com.example.stockgazer.domain.model

data class Stock(
    val change: Double,
    val percentChange: Double,
    val price: Double,
    val symbol: String,
) {
    fun isGain() = percentChange > 0
}