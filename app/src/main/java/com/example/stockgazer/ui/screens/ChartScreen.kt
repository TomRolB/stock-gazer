package com.example.stockgazer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.charts.CandlestickChart
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.theme.Loss300
import com.example.stockgazer.ui.theme.PrimaryLight

@Composable
fun ChartScreen() {
    Column(modifier = Modifier.fillMaxHeight()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Headline("MSFT")
            Icon(
                painter = painterResource(
                    id = R.drawable.four_star_outlined
                ),
                contentDescription = "",
                tint = PrimaryLight
            )
        }
        Text("Microsoft Corporation Common Stock", color = PrimaryLight)

        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("239.07", color = PrimaryLight)
            Text("-0.46%", fontWeight = FontWeight.Bold, color = Loss300)
        }

        CandlestickChart(modifier = Modifier.fillMaxHeight())
    }
}