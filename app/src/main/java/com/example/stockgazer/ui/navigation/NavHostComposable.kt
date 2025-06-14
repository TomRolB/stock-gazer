package com.example.stockgazer.ui.navigation

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stockgazer.ui.components.ChartEmptyState
import com.example.stockgazer.ui.screens.SearchScreen
import com.example.stockgazer.ui.screens.chart.ChartScreen
import com.example.stockgazer.ui.screens.home.HomeScreen
import com.example.stockgazer.ui.screens.home.HomeViewModel
import com.example.stockgazer.ui.theme.CircularProgressIndicatorSize
import com.example.stockgazer.ui.theme.PaddingMedium
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.material3.CircularProgressIndicator

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
            val homeViewModel: HomeViewModel = hiltViewModel()
            val homeLoadState by homeViewModel.homeLoadState.collectAsStateWithLifecycle()
            val favorites by homeViewModel.favorites.collectAsStateWithLifecycle()
            if (!homeLoadState.favoritesLoaded) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(CircularProgressIndicatorSize)
                    )
                }
            } else if (favorites.isNotEmpty()) {
                ChartScreen(favorites.first().symbol)
            } else {
                ChartEmptyState(navController)
            }
        }

        composable(route = StockGazerScreen.Chart.name + "/{symbol}") { backstackEntry ->
            val symbolArg = backstackEntry.arguments?.getString("symbol")
            val homeViewModel: HomeViewModel = hiltViewModel()
            val favorites by homeViewModel.favorites.collectAsStateWithLifecycle()
            val symbol = symbolArg ?: favorites.firstOrNull()?.symbol
            if (symbol != null) {
                ChartScreen(symbol)
            } else {
                ChartEmptyState(navController)
            }
        }

        composable(route = StockGazerScreen.Search.name) {
            SearchScreen(navController)
        }
    }
}