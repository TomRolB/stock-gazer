package com.example.stockgazer.domain.model

data class FollowedStockData(
    val symbol: String,
    val name: String,
    val percentChange: Double,
    val price: Double,
)