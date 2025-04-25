package com.example.stockgazer.ui.screens.home

import com.example.stockgazer.data.response.MostActiveStockResponse
import com.example.stockgazer.data.response.SnapshotResponse


data class ActiveStock (
    val symbol: String,
    val percentChange: Double,
    val tradeCount: Long,
    val volume: Long
) {
    companion object {
        fun listFrom(
            mostActiveStockResponse: MostActiveStockResponse,
            snapshots: Map<String, SnapshotResponse>
        ) = mostActiveStockResponse.mostActives.map {
            val dailyBar = snapshots[it.symbol]!!.dailyBar
            ActiveStock(
                symbol = it.symbol,
                tradeCount = it.tradeCount,
                volume = it.volume,
                percentChange = (dailyBar.c / dailyBar.o - 1) * 100
            )
        }
    }
}