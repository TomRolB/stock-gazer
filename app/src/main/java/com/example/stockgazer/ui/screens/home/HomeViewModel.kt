package com.example.stockgazer.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockgazer.data.datasource.AlpacaBarDatasource
import com.example.stockgazer.data.datasource.AlpacaDetailsDatasource
import com.example.stockgazer.data.datasource.AlpacaStockDatasource
import com.example.stockgazer.data.response.DetailsResponse
import com.example.stockgazer.data.response.MostActiveStockResponse
import com.example.stockgazer.data.response.SnapshotResponse
import com.example.stockgazer.data.response.TopMarketMoversResponse
import com.example.stockgazer.data.storage.getFavoriteSymbols
import com.example.stockgazer.domain.model.FollowedStockData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alpacaStockDatasource: AlpacaStockDatasource,
    private val alpacaBarDatasource: AlpacaBarDatasource,
    private val detailsDatasource: AlpacaDetailsDatasource,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private var _favorites = MutableStateFlow(listOf<FollowedStockData>())
    val favorites = _favorites.asStateFlow()

    private var _topMarketMovers = MutableStateFlow(TopMarketMoversResponse())

    private var _mostActiveStock = MutableStateFlow(listOf<ActiveStock>())
    val mostActiveStock = _mostActiveStock.asStateFlow()

    private var _homeLoadState = MutableStateFlow(HomeLoadState())
    val homeLoadState = _homeLoadState.asStateFlow()

    init {
        loadFavorites()
        loadTopMarketMovers()
        loadMostActiveStock()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            getFavoriteSymbols(context).collect { symbols ->
                alpacaBarDatasource.getSnapshotFromSymbols(
                    symbols.toList(),
                    onSuccess = { setFavorites(it) },
                    onFail = {},
                    loadingFinished = {},
                )
            }
        }
    }

    private fun setFavorites(favoritesSnapshots: Map<String, SnapshotResponse>) {
        viewModelScope.launch {
            _favorites.emit(
                favoritesSnapshots.entries.map {
                    val symbol = it.key
                    val snapshot = it.value
                    FollowedStockData(
                        symbol,
                        "",
                        snapshot.dailyPercentChange,
                        snapshot.currentPrice
                    )
                }
            )
            _homeLoadState.emit(_homeLoadState.value.copy(favoritesLoaded = true))
        }
    }

    fun loadCompanyName(symbol: String) {
        detailsDatasource.getStockOverview(symbol,
            onSuccess = {
                updateStockWithCompanyName(symbol, it)
            },
            onFail = {},
            loadingFinished = {}
        )


    }

    private fun updateStockWithCompanyName(symbol: String, detailsResponse: DetailsResponse) {
        viewModelScope.launch {
            _favorites.emit(
                _favorites.value.map { stock ->
                    if (stock.symbol == symbol)
                        stock.copy(name = detailsResponse.name)
                    else
                        stock
                }
            )
        }
    }

    private fun loadTopMarketMovers() {
        alpacaStockDatasource.getTopMarketMovers(
            onSuccess = {
                viewModelScope.launch {
                    _topMarketMovers.emit(it)
                    _homeLoadState.emit(_homeLoadState.value.copy(topMarketMoversLoaded = true))
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
                    _homeLoadState.emit(_homeLoadState.value.copy(mostActiveStockLoaded = true))
                }
            },
            onFail = {},
            loadingFinished = {}
        )
    }
}