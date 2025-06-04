package com.example.stockgazer

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.example.stockgazer.ui.auth.BiometricAuthContext
import com.example.stockgazer.ui.auth.BiometricError
import com.example.stockgazer.ui.auth.BiometricErrorScreen
import com.example.stockgazer.ui.auth.BiometricAvailable
import com.example.stockgazer.ui.navigation.BottomBar
import com.example.stockgazer.ui.navigation.NavHostComposable
import com.example.stockgazer.ui.screens.home.HomeScreen
import com.example.stockgazer.ui.theme.Primary900
import com.example.stockgazer.ui.theme.StockGazerTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            StockGazerTheme {
                Scaffold(
                    containerColor = Primary900,
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(navController::navigate) },
                ) { innerPadding ->
                    BiometricAuthContext { biometricState ->
                        when (biometricState) {
                            is BiometricError -> BiometricErrorScreen(
                                innerPadding, biometricState.errorTitle, biometricState.errorMessage
                            )
                            BiometricAvailable -> NavHostComposable(innerPadding, navController)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StockGazerTheme {
        HomeScreen(rememberNavController())
    }
}