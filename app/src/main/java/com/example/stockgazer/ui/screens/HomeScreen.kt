package com.example.stockgazer.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.ActiveStockCardSection
import com.example.stockgazer.ui.components.StockTile
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.theme.ElementSpacing
import com.example.stockgazer.ui.theme.PrimaryLight

@Composable
fun HomeScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(ElementSpacing)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Headline("Follow List")
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                painter = painterResource(
                    id = R.drawable.four_star_filled),
                    contentDescription = "",
                    tint = PrimaryLight
            )
        }
        LazyColumn {
            val count = 5
            items(count) { index ->
                StockTile(
                    ticker = "MSFT",
                    name = "Microsoft Corporation Common Stock",
                    currentPrice = 239.07,
                    variation = if (index % 2 == 0) -1.59 else 1.59,
                )
                if (index < count - 1) HorizontalDivider(
                    color = PrimaryLight.copy(alpha = 0.5f),
                    modifier = Modifier.padding(horizontal = 64.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))
        Headline("Most Active Stock")
        ActiveStockCardSection()
    }
}