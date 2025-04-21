package com.example.stockgazer.ui.screens.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockgazer.data.datasource.AlpacaBarDatasource
import com.example.stockgazer.util.ResourceZoneIdProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val alpacaBarDatasource: AlpacaBarDatasource, val zoneIdProvider: ResourceZoneIdProvider
) : ViewModel() {
    private var _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private var _showTradeCreationModal = MutableStateFlow(false)
    val showTradeCreationModal = _showTradeCreationModal.asStateFlow()

    private var _bars = MutableStateFlow(BarPeriod())
    val bars = _bars.asStateFlow()

    private val defaultTrade = Trade(
        date = LocalDate.now(zoneIdProvider.getTimeZone()),
        time = LocalTime.now(zoneIdProvider.getTimeZone()),
    )
    private var _currentTrade = MutableStateFlow(
        defaultTrade
    )
    val currentTrade = _currentTrade.asStateFlow()

    private var _trades = MutableStateFlow(emptyList<Trade>())
    val trades = _trades.asStateFlow()


    init {
        loadBars()
    }

    private fun loadBars() {
        alpacaBarDatasource.getBarsFromSymbol("AAPL", onSuccess = {
            viewModelScope.launch {
                _bars.emit(BarPeriod.fromBarResponse(it))
            }
        }, onFail = {
            // TODO: skeleton? Fail image?

        }, loadingFinished = {
            // TODO: ???
        })
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _isFavorite.emit(!_isFavorite.value)
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

    fun updateTradeAmount(amount: Int) {
        viewModelScope.launch {
            _currentTrade.emit(
                _currentTrade.value.copy(amount = amount)
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
            _currentTrade.emit(defaultTrade)
            _showTradeCreationModal.emit(false)
        }
    }
}