package com.example.stockgazer.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.ActiveStockCardSection
import com.example.stockgazer.ui.components.StockTile
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.navigation.StockGazerScreen
import com.example.stockgazer.ui.theme.CardBorderRadius
import com.example.stockgazer.ui.theme.CircularProgressIndicatorSize
import com.example.stockgazer.ui.theme.DividerHorizontalPadding
import com.example.stockgazer.ui.theme.ElementSpacing
import com.example.stockgazer.ui.theme.HeadlineToIconSpacing
import com.example.stockgazer.ui.theme.PaddingMedium
import com.example.stockgazer.ui.theme.Primary100
import com.example.stockgazer.ui.theme.Primary800
import com.example.stockgazer.ui.theme.SectionSpacing

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val mostActiveStock: List<ActiveStock> by viewModel.mostActiveStock.collectAsStateWithLifecycle()
    val homeLoadState by viewModel.homeLoadState.collectAsState()

    if (!homeLoadState.all()) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(CircularProgressIndicatorSize))
        }
        return
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(ElementSpacing),
        modifier = Modifier.fillMaxHeight(),
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
            FollowListSection(navController)
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
    navController: NavHostController
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val followList by viewModel.favorites.collectAsState()
    var count = 0

    if (followList.isEmpty()) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Primary800),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(CardBorderRadius),
        ) {
            Box(
                modifier = Modifier.padding(PaddingMedium)
            ) {
                Text(stringResource(R.string.empty_followed_stock_message), color = Primary100)
            }
        }

        return
    }

    Column {
        followList.forEach { stock ->
            viewModel.loadCompanyName(stock.symbol)

            StockTile(symbol = stock.symbol,
                name = stock.name,
                price = stock.price,
                variation = stock.percentChange,
                modifier = Modifier.clickable {
                    navController.navigate("${StockGazerScreen.Chart.name}/${stock.symbol}")
                })
            if (count < followList.size) HorizontalDivider(
                color = Primary100.copy(alpha = 0.5f),
                modifier = Modifier.padding(horizontal = DividerHorizontalPadding)
            )
            count++
        }
    }
}