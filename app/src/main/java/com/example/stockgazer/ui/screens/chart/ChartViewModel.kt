package com.example.stockgazer.ui.screens.chart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockgazer.data.datasource.AlpacaBarDatasource
import com.example.stockgazer.data.datasource.AlpacaDetailsDatasource
import com.example.stockgazer.data.response.SnapshotResponse
import com.example.stockgazer.data.storage.addFavoriteSymbol
import com.example.stockgazer.data.storage.isSymbolFavorite
import com.example.stockgazer.data.storage.removeFavoriteSymbol
import com.example.stockgazer.util.ResourceZoneIdProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val alpacaBarDatasource: AlpacaBarDatasource,
    private val alpacaDetailsDatasource: AlpacaDetailsDatasource,
    val zoneIdProvider: ResourceZoneIdProvider,
    @ApplicationContext private val context: Context,
    ) : ViewModel() {
    private var _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private var _showTradeCreationModal = MutableStateFlow(false)
    val showTradeCreationModal = _showTradeCreationModal.asStateFlow()

    private var _companyInfo = MutableStateFlow(CompanyInfo("", ""))
    val companyInfo = _companyInfo.asStateFlow()

    private var _latestPrice = MutableStateFlow(
        LatestPrice(
            value = 0.0,
            dailyPercentChange = 0.0
        )
    )
    val latestPrice = _latestPrice.asStateFlow()

    private var _bars = MutableStateFlow(BarPeriod())
    val bars = _bars.asStateFlow()

    private val defaultTrade = Trade(
        date = LocalDate.now(zoneIdProvider.getTimeZone()),
        time = LocalTime.now(zoneIdProvider.getTimeZone()),
    )
    private var _currentTrade = MutableStateFlow(defaultTrade)
    val currentTrade = _currentTrade.asStateFlow()

    private var _trades = MutableStateFlow(emptyList<Trade>())
    val trades = _trades.asStateFlow()

    fun load(symbol: String) {
        loadDetails(symbol)
        loadSnapshot(symbol)
        loadBars(symbol)
        loadIsFavoriteFromDataStore(symbol)
    }

    private fun loadDetails(symbol: String) {
        alpacaDetailsDatasource.getStockOverview(symbol,
            onSuccess = {
                viewModelScope.launch {
                    _companyInfo.emit(CompanyInfo(it.name, symbol))
                }
            },
            onFail = {},
            loadingFinished = {}
        )
    }

    private fun loadSnapshot(symbol: String) {
        alpacaBarDatasource.getSnapshotFromSymbols(listOf(symbol),
            onSuccess = {
                val symbolsSnapshot: SnapshotResponse = it[symbol]!!

                viewModelScope.launch {
                    val latestPriceFetched = LatestPrice.fromSnapshotResponse(symbolsSnapshot)
                    if (!_latestPrice.value.isLoaded()) {
                        _currentTrade.emit(_currentTrade.value.copy(price = latestPriceFetched.value.toString()))
                    }
                    _latestPrice.emit(latestPriceFetched)
                }
            }, onFail = {},
            loadingFinished = {}
        )
    }

    private fun loadBars(symbol: String) {
        alpacaBarDatasource.getBarsFromSymbol(symbol,
            onSuccess = {
                viewModelScope.launch {
                    _bars.emit(BarPeriod.fromBarResponse(it))
                }
            },
            onFail = {},
            loadingFinished = {})
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val isNowFavorite = !_isFavorite.value
            val symbol = _companyInfo.value.symbol

            if (isNowFavorite)
                addFavoriteSymbol(context, symbol)
            else
                removeFavoriteSymbol(context, symbol)

            loadIsFavoriteFromDataStore(symbol)
        }
    }

    private fun loadIsFavoriteFromDataStore(symbol: String) {
        viewModelScope.launch {
            isSymbolFavorite(context, symbol).collect {
                _isFavorite.emit(it)
            }
        }
    }

    fun toggleAddingTrade() {
        viewModelScope.launch {
            _showTradeCreationModal.emit(!_showTradeCreationModal.value)
        }
    }

    fun toggleTradeType() {
        viewModelScope.launch {
            _currentTrade.emit(
                _currentTrade.value.copy(type = !_currentTrade.value.type)
            )
        }
    }

    fun updateTradeAmount(amount: String) {
        viewModelScope.launch {
            _currentTrade.emit(
                _currentTrade.value.copy(amount = amount)
            )
        }
    }

    fun updateTradePrice(price: String) {
        viewModelScope.launch {
            _currentTrade.emit(
                _currentTrade.value.copy(price = price)
            )
        }
    }


    fun updateTradeDate(date: LocalDate) {
        viewModelScope.launch {
            _currentTrade.emit(
                _currentTrade.value.copy(date = date)
            )
        }
    }

    fun updateTradeTime(time: LocalTime) {
        viewModelScope.launch {
            _currentTrade.emit(
                _currentTrade.value.copy(time = time)
            )
        }
    }

    fun submitTrade() {
        viewModelScope.launch {
            _trades.emit(_trades.value + _currentTrade.value)
            _currentTrade.emit(
                defaultTrade.copy(price = _latestPrice.value.value.toString())
            )
            _showTradeCreationModal.emit(false)
        }
    }
}