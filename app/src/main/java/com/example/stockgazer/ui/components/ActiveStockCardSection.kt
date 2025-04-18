package com.example.stockgazer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
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

    LazyHorizontalGrid(
        horizontalArrangement = Arrangement.spacedBy(ElementSpacing),
        verticalArrangement = Arrangement.spacedBy(ElementSpacing),
        rows = GridCells.Fixed(2),
        modifier = Modifier.height(144.dp)
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
                logoUrl = logoUrl,
                modifier = Modifier.width(240.dp).height(64.dp)
            )
        }
    }
}