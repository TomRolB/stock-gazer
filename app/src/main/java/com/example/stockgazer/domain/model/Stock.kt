package com.example.stockgazer.domain.model

data class Stock(
    val symbol: String,
    val change: Double,
    val percentChange: Double,
    val price: Double,
)