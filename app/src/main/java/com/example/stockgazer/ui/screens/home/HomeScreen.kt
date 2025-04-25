package com.example.stockgazer.ui.screens.home
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.stockgazer.R
import com.example.stockgazer.data.repository.FakeStockRepository
import com.example.stockgazer.data.response.TopMarketMoversResponse
import com.example.stockgazer.ui.components.ActiveStockCardSection
import com.example.stockgazer.ui.components.StockTile
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.navigation.StockGazerScreen
import com.example.stockgazer.ui.theme.DividerHorizontalPadding
import com.example.stockgazer.ui.theme.ElementSpacing
import com.example.stockgazer.ui.theme.HeadlineToIconSpacing
import com.example.stockgazer.ui.theme.Primary100
import com.example.stockgazer.ui.theme.SectionSpacing

@Composable
fun HomeScreen(navController: NavHostController) {
    val stockRepository = FakeStockRepository()
    val viewModel = hiltViewModel<HomeViewModel>()
    val topMarketMovers: TopMarketMoversResponse by viewModel.topMarketMovers.collectAsStateWithLifecycle()
    val mostActiveStock: List<ActiveStock> by viewModel.mostActiveStock.collectAsStateWithLifecycle()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(ElementSpacing),
        modifier = Modifier
            .fillMaxHeight(),
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Headline(stringResource(R.string.follow_list_title))
                Spacer(modifier = Modifier.size(HeadlineToIconSpacing))
                Icon(
                    painter = painterResource(id = R.drawable.four_star_filled),
                    contentDescription = stringResource(R.string.four_star_icon_content_description),
                    tint = Primary100
                )
            }
        }

        item {
            FollowListSection(stockRepository, navController)
        }

        item {
            Spacer(modifier = Modifier.size(SectionSpacing))
        }

        item {
            Headline(stringResource(R.string.most_active_stock_title))
        }

        item {
            ActiveStockCardSection(navController, mostActiveStock)
        }

        item {
            Spacer(modifier = Modifier.size(SectionSpacing))
        }
    }
}

@Composable
private fun FollowListSection(
    stockRepository: FakeStockRepository,
    navController: NavHostController
) {
    val followList = stockRepository.getFollowList()
    var count = 0

    Column {
        followList.forEach { stock ->
            StockTile(
                symbol = stock.symbol,
                name = stock.name,
                price = stock.price,
                variation = stock.percentChange,
                modifier = Modifier.clickable {
                    navController.navigate("${StockGazerScreen.Chart.name}/${stock.symbol}" )
                }
            )
            if (count < followList.size) HorizontalDivider( // TODO should be placed better
                color = Primary100.copy(alpha = 0.5f),
                modifier = Modifier.padding(horizontal = DividerHorizontalPadding)
            )
            count++
        }
    }
}