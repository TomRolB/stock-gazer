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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.stockgazer.R
import com.example.stockgazer.data.repository.FakeStockRepository
import com.example.stockgazer.ui.navigation.StockGazerScreen
import com.example.stockgazer.ui.screens.home.ActiveStock
import com.example.stockgazer.ui.theme.ActiveStockCardHeight
import com.example.stockgazer.ui.theme.ActiveStockCardSectionHeight
import com.example.stockgazer.ui.theme.ActiveStockCardWidth
import com.example.stockgazer.ui.theme.ElementSpacing


@Composable
fun ActiveStockCardSection(navController: NavController, mostActiveStock: List<ActiveStock>) {
    FakeStockRepository()

    LazyHorizontalGrid(
        horizontalArrangement = Arrangement.spacedBy(ElementSpacing),
        verticalArrangement = Arrangement.spacedBy(ElementSpacing),
        rows = GridCells.Fixed(2),
        modifier = Modifier.height(ActiveStockCardSectionHeight)
    ) {
        items(items = mostActiveStock) { stock ->
            val logoUrl = stringResource(R.string.logos_url) + "${stock.symbol}.png"
            ActiveStockCard(
                isGain = stock.percentChange > 0,
                symbol = stock.symbol,
                variation = stock.percentChange,
                trades = stock.tradeCount,
                volume = stock.volume,
                modifier = Modifier
                    .width(ActiveStockCardWidth)
                    .height(ActiveStockCardHeight)
                    .clickable {
                        navController.navigate("${StockGazerScreen.Chart.name}/${stock.symbol}")
                    }
            )
        }
    }
}