package com.example.stockgazer.ui.screens.chart

import com.example.stockgazer.data.response.SnapshotResponse

class LatestPrice(
    val value: Double,
    val dailyVariation: Double
) {
    companion object {
        fun fromSnapshotResponse(snapshotResponse: SnapshotResponse) =
            LatestPrice(
                snapshotResponse.dailyBar.c,
                (snapshotResponse.dailyBar.c / snapshotResponse.dailyBar.o - 1) * 100
            )
    }
}