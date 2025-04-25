package com.example.stockgazer.ui.screens.chart

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.YourTradesSection
import com.example.stockgazer.ui.components.charts.CandlestickChart
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.NumbersHorizontalSpacing
import com.example.stockgazer.ui.theme.Primary100
import com.example.stockgazer.ui.theme.SectionSpacing
import com.example.stockgazer.util.asPercentageString

@Composable
fun ChartScreen(symbol: String) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val viewModel = hiltViewModel<ChartViewModel>()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val latestPrice by viewModel.latestPrice.collectAsState()
    val companyName by viewModel.companyName.collectAsState()

    viewModel.load(symbol)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxHeight(),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Headline(symbol)
            IconButton(
                onClick = { viewModel.toggleFavorite() }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isFavorite) R.drawable.four_star_filled
                            else R.drawable.four_star_outlined
                    ),
                    contentDescription = stringResource(R.string.four_star_icon_content_description),
                    tint = Primary100
                )
            }
        }
        Text(companyName, color = Primary100)

        Spacer(modifier = Modifier.height(SectionSpacing))
        Row(horizontalArrangement = Arrangement.spacedBy(NumbersHorizontalSpacing)) {
            Text(latestPrice.value.toString(), color = Primary100)
            Text(
                text = latestPrice.dailyPercentChange.asPercentageString(),
                fontWeight = FontWeight.Bold,
                color = Loss300
            )
        }

        CandlestickChart(modifier = Modifier.height(screenHeight * 0.65f))

        Spacer(modifier = Modifier.height(SectionSpacing))

        YourTradesSection(latestPrice)
    }
}
