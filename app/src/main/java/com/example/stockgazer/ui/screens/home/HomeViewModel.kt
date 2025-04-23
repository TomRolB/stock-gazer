package com.example.stockgazer.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockgazer.data.datasource.AlpacaStockDatasource
import com.example.stockgazer.data.response.MostActiveStockResponse
import com.example.stockgazer.data.response.TopMarketMoversResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val alpacaStockDatasource: AlpacaStockDatasource,
) : ViewModel() {
    private var _topMarketMovers = MutableStateFlow(TopMarketMoversResponse())
    val topMarketMovers = _topMarketMovers.asStateFlow()

    private var _mostActiveStock = MutableStateFlow(MostActiveStockResponse("", listOf()))
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
            onFail = {
                // TODO: does it really have sense to retry an API call?
            },
            loadingFinished = {
                // TODO: skeletons?
            }
        )


    }

    private fun loadMostActiveStock() {
        alpacaStockDatasource.getMostActiveStock(
            onSuccess = {
                viewModelScope.launch {
                    _mostActiveStock.emit(it)
                }
            },
            onFail = {
                // TODO: does it really have sense to retry an API call?
            },
            loadingFinished = {
                // TODO: skeletons?
            }
        )
    }
}