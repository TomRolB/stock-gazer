package com.example.stockgazer.ui.screens.chart

data class ChartLoadState(
    val detailsLoaded: Boolean = false,
    val snapshotLoaded: Boolean = false,
    val barsLoaded: Boolean = false,
) {
    fun all() = detailsLoaded && snapshotLoaded && barsLoaded
}