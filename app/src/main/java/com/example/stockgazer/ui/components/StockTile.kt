package com.example.stockgazer.ui.components

import android.provider.CalendarContract.Colors
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stockgazer.ui.theme.Gain300
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.MontserratFontFamily
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
           .padding(16.dp)
   ) {
//       Image()
       Column {
           Text(
               ticker,
               color = PrimaryLight,
               fontWeight = FontWeight.Bold,
           )
           Text(
               name,
               color = PrimaryLight,
           )
       }
       Spacer(modifier = Modifier.width(16.dp))
       Column(horizontalAlignment = Alignment.End) {
           Text(
               currentPrice.toString(),
               textAlign = TextAlign.Right,
               color = PrimaryLight,
           )
           Text(
               "$variation%",
               color = if (isGain) Gain300 else Loss300,
               textAlign = TextAlign.Right,
               fontWeight = FontWeight.Bold
           )
       }
   }
}