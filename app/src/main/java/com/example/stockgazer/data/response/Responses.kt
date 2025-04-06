package com.example.stockgazer.data.response

import com.example.stockgazer.domain.model.Stock

data class TopMarketMoversResponse(
    val gainers: List<Stock>,
    val losers: List<Stock>,
)

data class BarsResponse(
    val opening: List<Double>,
    val closing: List<Double>,
    val low: List<Double>,
    val high: List<Double>,
)