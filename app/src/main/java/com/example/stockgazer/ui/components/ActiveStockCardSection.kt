package com.example.stockgazer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stockgazer.data.repository.FakeStockRepository
import com.example.stockgazer.ui.theme.ElementSpacing

@Composable
fun ActiveStockCardSection() {
    val stockRepository = FakeStockRepository()

    // TODO: cards are not the same width

    LazyHorizontalGrid(
        // TODO: modifier below is hardcoded; need to find a way of better
        //  preventing cards from taking as much height as they can
        modifier = Modifier.height(136.dp),
        horizontalArrangement = Arrangement.spacedBy(ElementSpacing),
        verticalArrangement = Arrangement.spacedBy(ElementSpacing),
        rows = GridCells.Fixed(2)
    ) {
        val mostActiveStockList = stockRepository.getMostActiveStock()

        items(items = mostActiveStockList) { stock ->
            val logoUrl = stockRepository.getCompanyProfile(stock.symbol).logoUrl
            ActiveStockCard(
                isGain = true,
                symbol = stock.symbol,
                trades = stock.tradeCount,
                volume = stock.volume,
                variation = 0.01,
                logoUrl = logoUrl
            )
        }
    }
}