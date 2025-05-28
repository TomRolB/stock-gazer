package com.example.stockgazer.ui.screens.home

data class HomeLoadState(
    val favoritesLoaded: Boolean = false,
    val topMarketMoversLoaded: Boolean = false,
    val mostActiveStockLoaded: Boolean = false,
) {
    fun all() =
        favoritesLoaded && topMarketMoversLoaded && mostActiveStockLoaded
}