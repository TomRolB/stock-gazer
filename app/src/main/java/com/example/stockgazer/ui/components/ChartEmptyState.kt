package com.example.stockgazer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.text.Headline
import com.example.stockgazer.ui.navigation.StockGazerScreen
import com.example.stockgazer.ui.theme.SectionSpacing

@Composable
fun ChartEmptyState(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Headline(stringResource(R.string.chart_empty_title))
            Spacer(modifier = Modifier.height(SectionSpacing))
            Button(
                onClick = { navController.navigate(StockGazerScreen.Search.name) }
            ) {
                Text(
                    text = stringResource(R.string.chart_empty_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
} 