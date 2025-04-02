package com.example.stockgazer.domain.model

data class StockMovement(
    val symbol: String,
    val tradeCount: Long,
    val volume: Long,
)
