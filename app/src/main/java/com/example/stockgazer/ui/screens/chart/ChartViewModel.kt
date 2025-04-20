package com.example.stockgazer.ui.screens.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockgazer.data.datasource.AlpacaBarDatasource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val alpacaBarDatasource: AlpacaBarDatasource
) : ViewModel() {
    private var _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private var _isAddingTrade = MutableStateFlow(false)
    val isAddingTrade = _isAddingTrade.asStateFlow()

    private var _bars = MutableStateFlow(BarPeriod())
    val bars = _bars.asStateFlow()

    init {
        loadBars()
    }

    private fun loadBars() {
        alpacaBarDatasource.getBarsFromSymbol(
            "AAPL",
            onSuccess = {
                viewModelScope.launch {
                    _bars.emit(BarPeriod.fromBarResponse(it))
                }
            },
            onFail = {
                // TODO: skeleton? Fail image?

            },
            loadingFinished = {
                // TODO: ???
            }
        )
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _isFavorite.emit(!_isFavorite.value)
        }
    }

    fun toggleAddingTrade() {
        viewModelScope.launch {
            _isAddingTrade.emit(!_isAddingTrade.value)
        }
    }
}