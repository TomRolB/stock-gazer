package com.example.stockgazer.ui.navigation

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stockgazer.ui.screens.SearchScreen
import com.example.stockgazer.ui.screens.chart.ChartScreen
import com.example.stockgazer.ui.screens.home.HomeScreen
import com.example.stockgazer.ui.theme.PaddingMedium
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    LaunchedEffect(Unit) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

    NavHost(
        navController = navController,
        startDestination = StockGazerScreen.Home.name,
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(PaddingMedium)
    ) {
        composable(route = StockGazerScreen.Home.name) {
            HomeScreen(navController)
        }

        composable(route = StockGazerScreen.Chart.name) {
            ChartScreen("MSFT") // TODO (2do parcial): use a starred stock, from the DB
        }

        composable(route = StockGazerScreen.Chart.name + "/{symbol}") { backstackEntry ->
            val symbol = backstackEntry.arguments
                ?.getString("symbol")
                ?: "MSFT" // TODO (2do parcial): use a starred stock, from the DB

            ChartScreen(symbol)
        }

        composable(route = StockGazerScreen.Search.name) {
            SearchScreen()
        }
    }
}