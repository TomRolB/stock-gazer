package com.example.stockgazer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.stockgazer.data.repository.FakeStockRepository
import com.example.stockgazer.ui.navigation.StockGazerScreen
import com.example.stockgazer.ui.screens.home.ActiveStock
import com.example.stockgazer.ui.theme.ActiveStockCardHeight
import com.example.stockgazer.ui.theme.ActiveStockCardSectionHeight
import com.example.stockgazer.ui.theme.ActiveStockCardWidth
import com.example.stockgazer.ui.theme.ElementSpacing

// TODO: move to screens/home? That would mean each screen would have its own components directory

@Composable
fun ActiveStockCardSection(navController: NavController, mostActiveStock: List<ActiveStock>) {
    val stockRepository = FakeStockRepository()

    LazyHorizontalGrid(
        horizontalArrangement = Arrangement.spacedBy(ElementSpacing),
        verticalArrangement = Arrangement.spacedBy(ElementSpacing),
        rows = GridCells.Fixed(2),
        modifier = Modifier.height(ActiveStockCardSectionHeight)
    ) {
        items(items = mostActiveStock) { stock ->
            val logoUrl = stockRepository.getCompanyProfile(stock.symbol).logoUrl
            ActiveStockCard(
                isGain = stock.percentChange > 0,
                symbol = stock.symbol,
                trades = stock.tradeCount,
                volume = stock.volume,
                variation = stock.percentChange,
                logoUrl = logoUrl,
                modifier = Modifier
                    .width(ActiveStockCardWidth)
                    .height(ActiveStockCardHeight)
                    .clickable {
                        navController.navigate("${StockGazerScreen.Chart.name}/${stock.symbol}" )
                    }
            )
        }
    }
}