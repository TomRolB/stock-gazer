package com.example.stockgazer.ui.screens.chart

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stockgazer.R
import com.example.stockgazer.data.response.TopMarketMoversResponse
import com.example.stockgazer.ui.components.TradeRegister
import com.example.stockgazer.ui.components.charts.CandlestickChart
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.screens.home.HomeViewModel
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.Primary100

@Composable
fun ChartScreen() {
    val configuration = LocalConfiguration.current
    // Convert the screen height from dp to Dp type
    val screenHeight = configuration.screenHeightDp.dp

    val viewModel = hiltViewModel<ChartViewModel>()
    val isFavorite by viewModel.isFavorite.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxHeight(),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Headline("MSFT")
            IconButton(
                onClick = { viewModel.toggleFavorite() }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isFavorite) R.drawable.four_star_filled
                            else R.drawable.four_star_outlined
                    ),
                    contentDescription = "",
                    tint = Primary100
                )
            }
        }
        Text("Microsoft Corporation Common Stock", color = Primary100)

        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("239.07", color = Primary100)
            Text("-0.46%", fontWeight = FontWeight.Bold, color = Loss300)
        }

        CandlestickChart(modifier = Modifier.height(screenHeight * 0.65f))

        Spacer(modifier = Modifier.height(32.dp))


        Headline("Your trades")

        Spacer(modifier = Modifier.height(16.dp))

        repeat(10) {
            TradeRegister(1, "01/01/2024", "13:43", 5600.0, 0.6)
        }
    }
}