package com.example.stockgazer.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stockgazer.ui.screens.ChartScreen
import com.example.stockgazer.ui.screens.HomeScreen
import com.example.stockgazer.ui.screens.SearchScreen

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = StockGazerScreen.Chart.name,
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(20.dp)
    ) {
        composable(route = StockGazerScreen.Home.name) {
            HomeScreen()
        }

        composable(route = StockGazerScreen.Chart.name) {
            ChartScreen()
        }

        composable(route = StockGazerScreen.Search.name) {
            SearchScreen()
        }
    }
}