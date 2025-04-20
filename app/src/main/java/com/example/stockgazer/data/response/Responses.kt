package com.example.stockgazer.data.response

import com.example.stockgazer.domain.model.Stock

data class TopMarketMoversResponse(
    val gainers: List<Stock> = listOf(),
    val losers: List<Stock> = listOf(),
)

//TODO: won't work because data is returned in other format.
data class BarsResponse(val bars: List<Bar>)

data class Bar (
    val o: Double,  // open
    val c: Double,  // close
    val h: Double,  // high
    val l: Double,  // low
    val n: Int,     // number of trades
    val t: String,  // timestamp
    val v: Int,     // volume
    val vw: Double  // volume-weighted average
)
