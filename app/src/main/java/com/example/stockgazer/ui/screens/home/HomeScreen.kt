package com.example.stockgazer.ui.screens.home
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stockgazer.R
import com.example.stockgazer.data.repository.FakeStockRepository
import com.example.stockgazer.data.response.TopMarketMoversResponse
import com.example.stockgazer.ui.components.ActiveStockCardSection
import com.example.stockgazer.ui.components.StockTile
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.theme.ElementSpacing
import com.example.stockgazer.ui.theme.Primary100

@Composable
fun HomeScreen() {
    val stockRepository = FakeStockRepository();
    val viewModel = hiltViewModel<HomeViewModel>()
    val topMarketMovers: TopMarketMoversResponse by viewModel.topMarketMovers.collectAsStateWithLifecycle()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(ElementSpacing),
        modifier = Modifier
            .fillMaxHeight(),
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Headline(stringResource(R.string.follow_list))
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.four_star_filled),
                    contentDescription = "",
                    tint = Primary100
                )
            }
        }


        item {
            FollowListSection(stockRepository)
        }

        item {
            Spacer(modifier = Modifier.size(16.dp))
        }

        item {
            Headline(stringResource(R.string.most_active_stock))
        }

        item {
            ActiveStockCardSection()
        }

        item {
            Spacer(modifier = Modifier.size(16.dp))
        }

        item {
            Headline(stringResource(R.string.top_market_movers))
        }

        items(items = topMarketMovers.gainers) { gainer ->
            StockTile(
                symbol = gainer.symbol,
                name = "adsa",
                price = gainer.price,
                variation = gainer.percentChange
            )
        }
    }
}

@Composable
private fun FollowListSection(stockRepository: FakeStockRepository) {
    val followList = stockRepository.getFollowList();
    var count = 0

    Column {
        followList.forEach { stock ->
            StockTile(
                symbol = stock.symbol,
                name = stock.name,
                price = stock.price,
                variation = stock.change,
            )
            if (count < followList.size) HorizontalDivider(
                color = Primary100.copy(alpha = 0.5f),
                modifier = Modifier.padding(horizontal = 64.dp)
            )
            count++
        }
    }
}