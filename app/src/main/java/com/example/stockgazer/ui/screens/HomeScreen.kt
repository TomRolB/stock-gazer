package com.example.stockgazer.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stockgazer.ui.components.ActiveStockCard
import com.example.stockgazer.ui.components.StockTile
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.theme.ElementSpacing
import com.example.stockgazer.ui.theme.PrimaryLight

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(ElementSpacing)
    ) {
        Headline("Follow List")
        LazyColumn {
            val count = 5
            items(count) { index ->
                StockTile(
                    isGain = index % 2 == 0,
                    ticker = "MSFT",
                    name = "Microsoft Corporation Common Stock",
                    currentPrice = 239.07,
                    variation = 1.59,
                )
                if (index < count - 1) HorizontalDivider(
                    color = PrimaryLight.copy(alpha = 0.5f),
                    modifier = Modifier.padding(horizontal = 64.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Headline("Most Active Stock")
        LazyHorizontalGrid(
            // TODO: modifier below is hardcoded; need to find a way of better
            //  preventing cards from taking as much height as they can
            modifier = Modifier.height(136.dp),
            horizontalArrangement = Arrangement.spacedBy(ElementSpacing),
            verticalArrangement = Arrangement.spacedBy(ElementSpacing),
            rows = GridCells.Fixed(2)
        ) {
            items(5) { index ->
                ActiveStockCard(
                    isGain = index % 2 == 0,
                    ticker = "MSFT",
                    trades = 319245,
                    volume = 19487247L,
                    variation = 1.59,
                )
            }
        }
    }
}