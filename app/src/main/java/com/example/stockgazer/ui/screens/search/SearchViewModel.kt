package com.example.stockgazer.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockgazer.data.datasource.AlpacaAssetsDatasource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val alpacaAssetsDatasource: AlpacaAssetsDatasource
) : ViewModel() {
    private val _symbols = MutableStateFlow<List<String>>(emptyList())
    val symbols: StateFlow<List<String>> = _symbols.asStateFlow()

    init {
        viewModelScope.launch {
            alpacaAssetsDatasource.getAllAssets(
                onSuccess = { list -> _symbols.value = list },
                onFail = { /* ignore failures */ },
                loadingFinished = { /* no-op */ }
            )
        }
    }
} 