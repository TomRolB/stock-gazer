package com.example.stockgazer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.stockgazer.ui.theme.Gain300
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.PrimaryDark
import com.example.stockgazer.ui.theme.PrimaryLight

@Composable
fun StockTile(
    isGain: Boolean,
    ticker: String,
    name: String,
    currentPrice: Double,
    variation: Double,
) {
   Row(
       modifier = Modifier
           .background(PrimaryDark)
           .padding(vertical = 16.dp),
       horizontalArrangement = Arrangement.spacedBy(16.dp)
   ) {
       AsyncImage(
           model = "https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/MSFT.png",
           contentDescription = "$name's logo",
           modifier = Modifier
               .size(width = 42.dp, height = 42.dp)
               .clip(shape = CircleShape)
       )
       Column(modifier = Modifier.weight(1f)) {
           Text(
               ticker,
               color = PrimaryLight,
               fontWeight = FontWeight.Bold,
           )
           //TODO: may use a smaller font size, since not much of the name is finally rendered
           Text(
               name,
               color = PrimaryLight,
               maxLines = 1,
               overflow = TextOverflow.Ellipsis
           )
       }
       Column(horizontalAlignment = Alignment.End) {
           Text(
               currentPrice.toString(),
               textAlign = TextAlign.Right,
               color = PrimaryLight,
           )
           Text(
               "%$variation",
               color = if (isGain) Gain300 else Loss300,
               textAlign = TextAlign.Right,
               fontWeight = FontWeight.Bold
           )
       }
   }
}