package com.example.stockgazer.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockgazer.data.datasource.AlpacaBarDatasource
import com.example.stockgazer.data.datasource.AlpacaStockDatasource
import com.example.stockgazer.data.response.MostActiveStockResponse
import com.example.stockgazer.data.response.TopMarketMoversResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alpacaStockDatasource: AlpacaStockDatasource,
    private val alpacaBarDatasource: AlpacaBarDatasource,
) : ViewModel() {
    private var _topMarketMovers = MutableStateFlow(TopMarketMoversResponse())

    private var _mostActiveStock = MutableStateFlow(listOf<ActiveStock>())
    val mostActiveStock = _mostActiveStock.asStateFlow()

    init {
        loadTopMarketMovers()
        loadMostActiveStock()
    }

    private fun loadTopMarketMovers() {
        alpacaStockDatasource.getTopMarketMovers(
            onSuccess = {
                viewModelScope.launch {
                    _topMarketMovers.emit(it)
                }
            },
            onFail = {},
            loadingFinished = {}
        )


    }

    private fun loadMostActiveStock() {
        alpacaStockDatasource.getMostActiveStock(
            onSuccess = {
                loadPercentChanges(it)
            },
            onFail = {},
            loadingFinished = {}
        )
    }

    private fun loadPercentChanges(mostActiveStockResponse: MostActiveStockResponse) {
        val symbols = mostActiveStockResponse.mostActives.map { it.symbol }
        alpacaBarDatasource.getSnapshotFromSymbols(symbols,
            onSuccess = { snapshots ->
                viewModelScope.launch {
                    _mostActiveStock.emit(
                        ActiveStock.listFrom(mostActiveStockResponse, snapshots)
                    )
                }
            },
            onFail = {},
            loadingFinished = {}
        )
    }
}