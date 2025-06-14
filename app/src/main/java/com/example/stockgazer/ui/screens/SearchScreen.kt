package com.example.stockgazer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.stockgazer.R
import com.example.stockgazer.ui.components.SymbolTile
import com.example.stockgazer.ui.navigation.StockGazerScreen
import com.example.stockgazer.ui.screens.search.SearchViewModel

@Composable
fun SearchScreen(navController: NavHostController) {
    val viewModel: SearchViewModel = hiltViewModel()
    val symbols by viewModel.symbols.collectAsState()
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = {
                Text(
                    stringResource(R.string.search_symbols),
                    color = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary)
        )

        val filtered = if (query.isBlank()) emptyList() else {
            symbols.filter { it.startsWith(query, ignoreCase = true) }.take(50)
        }

        LazyColumn {
            items(filtered) { symbol ->
                SymbolTile(
                    symbol = symbol,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                "${StockGazerScreen.Chart.name}/$symbol"
                            )
                        }
                )
            }
        }
    }
}