package com.example.stockgazer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stockgazer.ui.components.StockTile
import com.example.stockgazer.ui.theme.PrimaryDark
import com.example.stockgazer.ui.theme.PrimaryLight
import com.example.stockgazer.ui.theme.StockGazerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockGazerTheme {
                Scaffold(
                    containerColor = PrimaryDark,
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = stringResource(R.string.app_name),
//        modifier = modifier
//    )
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Follow List",
            style = MaterialTheme.typography.headlineLarge,
            color = PrimaryLight,
            fontWeight = FontWeight.Bold
        )
        LazyColumn {
            items(5) {
                StockTile(
                    isGain = true,
                    ticker = "MSFT",
                    name = "Microsoft Corporation Common Stock",
                    currentPrice = 239.07,
                    variation = 1.59,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StockGazerTheme {
        Greeting("Android")
    }
}