package com.example.stockgazer.ui.screens.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockgazer.data.datasource.AlpacaBarDatasource
import com.example.stockgazer.data.response.BarsResponse
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

    private var _bars = MutableStateFlow(BarsResponse())
    val bars = _bars.asStateFlow()

    init {
        loadBars()
    }

    private fun loadBars() {
        alpacaBarDatasource.getBarsFromSymbol(
            "AAPL",
            onSuccess = {
                viewModelScope.launch {
                    _bars.emit(it)
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
}