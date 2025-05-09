package com.example.stockgazer.ui.screens.chart

import com.example.stockgazer.data.response.SnapshotResponse

class LatestPrice(
    val value: Double,
    val dailyPercentChange: Double
) {
    fun isLoaded() = value > 0 && dailyPercentChange > 0

    companion object {
        fun fromSnapshotResponse(snapshotResponse: SnapshotResponse) =
            LatestPrice(
                snapshotResponse.currentPrice,
                snapshotResponse.dailyPercentChange
            )
    }
}