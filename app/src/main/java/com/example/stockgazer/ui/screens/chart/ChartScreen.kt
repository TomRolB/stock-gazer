package com.example.stockgazer.ui.screens.chart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.example.stockgazer.ui.theme.CircularProgressIndicatorSize
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.NumbersHorizontalSpacing
import com.example.stockgazer.ui.theme.Primary100
import com.example.stockgazer.ui.theme.SectionSpacing
import com.example.stockgazer.util.asPercentageString

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ChartScreen(symbol: String) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val chartViewModel = hiltViewModel<ChartViewModel>()

    val isFavorite by chartViewModel.isFavorite.collectAsState(false)
    val latestPrice by chartViewModel.latestPrice.collectAsState()
    val companyInfo by chartViewModel.companyInfo.collectAsState()
    val loadState by chartViewModel.loadState.collectAsState()

    chartViewModel.load(symbol)

    if (!loadState.all()) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(CircularProgressIndicatorSize))
        }
        return
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxHeight(),
    ) {

        Header(symbol, chartViewModel, isFavorite, companyInfo)

        Spacer(modifier = Modifier.height(SectionSpacing))
        Row(horizontalArrangement = Arrangement.spacedBy(NumbersHorizontalSpacing)) {
            Text(latestPrice.value.toString(), color = Primary100)
            Text(
                text = latestPrice.dailyPercentChange.asPercentageString(),
                fontWeight = FontWeight.Bold,
                color = Loss300
            )
        }

        CandlestickChart(
            modifier = Modifier.height(screenHeight * 0.65f)
        )

        Spacer(modifier = Modifier.height(SectionSpacing))

        YourTradesSection(latestPrice)
    }
}

@Composable
private fun Header(
    symbol: String,
    viewModel: ChartViewModel,
    isFavorite: Boolean,
    companyInfo: CompanyInfo
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Headline(symbol)
        IconButton(
            onClick = { viewModel.toggleFavorite(isFavorite) }
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
    Text(companyInfo.name, color = Primary100)
}
