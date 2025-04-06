package com.example.stockgazer.domain.model

data class FollowedStockData(
    val symbol: String,
    val name: String,
    val change: Double,
    val percentChange: Double,
    val price: Double,
)