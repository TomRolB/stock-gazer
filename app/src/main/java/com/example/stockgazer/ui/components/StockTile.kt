package com.example.stockgazer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.stockgazer.data.repository.FakeStockRepository
import com.example.stockgazer.ui.components.images.Logo
import com.example.stockgazer.ui.theme.ElementSpacing
import com.example.stockgazer.ui.theme.Gain300
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.PaddingMedium
import com.example.stockgazer.ui.theme.Primary100
import com.example.stockgazer.ui.theme.Primary900
import com.example.stockgazer.util.asPercentageString

@Composable
fun StockTile(
    symbol: String,
    name: String,
    price: Double,
    variation: Double,
    modifier: Modifier = Modifier
) {
    val stockRepository = FakeStockRepository()

    Row(
       modifier = Modifier
           .background(Primary900)
           .padding(vertical = PaddingMedium)
           .then(modifier),
       horizontalArrangement = Arrangement.spacedBy(ElementSpacing),
       verticalAlignment = Alignment.CenterVertically
   ) {
       val logoUrl = stockRepository.getCompanyProfile(symbol).logoUrl // TODO: use url directly
       Logo(logoUrl, symbol)
       Column(modifier = Modifier.weight(1f)) {
           Text(
               symbol,
               color = Primary100,
               fontWeight = FontWeight.Bold,
           )
           //TODO: may use a smaller font size, since not much of the name is finally rendered
           Text(
               name,
               color = Primary100,
               maxLines = 1,
               overflow = TextOverflow.Ellipsis
           )
       }
       Column(horizontalAlignment = Alignment.End) {
           Text(
               price.toString(),
               textAlign = TextAlign.Right,
               color = Primary100,
           )
           Text(
               text = variation.asPercentageString(),
               color = if (variation < 0) Loss300 else Gain300,
               textAlign = TextAlign.Right,
               fontWeight = FontWeight.Bold
           )
       }
   }
}