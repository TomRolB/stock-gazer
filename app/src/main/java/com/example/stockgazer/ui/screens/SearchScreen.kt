package com.example.stockgazer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockgazer.ui.components.SymbolTile
import com.example.stockgazer.ui.screens.search.SearchViewModel

@Composable
fun SearchScreen() {
    val viewModel: SearchViewModel = hiltViewModel()
    val symbols by viewModel.symbols.collectAsState()
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search symbols") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        val filtered = if (query.isBlank()) emptyList() else {
            symbols.filter { it.startsWith(query, ignoreCase = true) }.take(50)
        }

        LazyColumn {
            items(filtered) { symbol ->
                SymbolTile(symbol = symbol, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}