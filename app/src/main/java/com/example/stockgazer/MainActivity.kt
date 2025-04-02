package com.example.stockgazer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.stockgazer.ui.navigation.BottomBar
import com.example.stockgazer.ui.navigation.NavHostComposable
import com.example.stockgazer.ui.screens.HomeScreen
import com.example.stockgazer.ui.theme.PrimaryDark
import com.example.stockgazer.ui.theme.StockGazerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            StockGazerTheme {
                Scaffold(
                    containerColor = PrimaryDark,
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(navController::navigate) },
                ) { innerPadding ->
                    NavHostComposable(innerPadding, navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StockGazerTheme {
        HomeScreen()
    }
}