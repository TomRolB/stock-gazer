package com.example.stockgazer.data.response

import com.example.stockgazer.domain.model.Stock
import com.google.gson.annotations.SerializedName

data class MostActiveStockResponse(
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("most_actives")
    val mostActives: List<StockMovement>
)

data class StockMovement (
    val symbol: String,
    @SerializedName("trade_count")
    val tradeCount: Long,
    val volume: Long
)

data class TopMarketMoversResponse(
    val gainers: List<Stock> = listOf(),
    val losers: List<Stock> = listOf(),
)

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

data class SnapshotResponse (
    val dailyBar: Bar,
) {
    val dailyPercentChange: Double
        get() = (dailyBar.c / dailyBar.o - 1) * 100

    val currentPrice: Double
        get() = dailyBar.c
}

data class DetailsResponse (
    val name: String
)
