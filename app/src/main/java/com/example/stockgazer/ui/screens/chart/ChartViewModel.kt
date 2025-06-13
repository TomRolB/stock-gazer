package com.example.stockgazer.ui.screens.chart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.stockgazer.data.datasource.AlpacaBarDatasource
import com.example.stockgazer.data.datasource.AlpacaDetailsDatasource
import com.example.stockgazer.data.entities.FavoriteStock
import com.example.stockgazer.data.entities.Trade
import com.example.stockgazer.data.response.SnapshotResponse
import com.example.stockgazer.storage.StockGazerDatabase
import com.example.stockgazer.util.ResourceZoneIdProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val alpacaBarDatasource: AlpacaBarDatasource,
    private val alpacaDetailsDatasource: AlpacaDetailsDatasource,
    val zoneIdProvider: ResourceZoneIdProvider,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _userIdFlow = MutableStateFlow(auth.currentUser?.uid)

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _userIdFlow.value = firebaseAuth.currentUser?.uid
        }
    }

    private val database = StockGazerDatabase.getDatabase(context)

    private var _showTradeCreationModal = MutableStateFlow(false)
    val showTradeCreationModal = _showTradeCreationModal.asStateFlow()

    private var _companyInfo = MutableStateFlow(CompanyInfo("", ""))
    val companyInfo = _companyInfo.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val isFavorite: StateFlow<Boolean> = combine(
        _companyInfo.filterNotNull(),
        _userIdFlow
    ) { companyInfo, uid -> companyInfo.symbol to uid }
    .flatMapLatest { (symbol, uid) ->
        if (uid == null) flowOf(false)
        else database.favoriteStockDao().isStockFavorite(symbol, uid).asFlow()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    private var _latestPrice = MutableStateFlow(
        LatestPrice(
            value = 0.0, dailyPercentChange = 0.0
        )
    )
    val latestPrice = _latestPrice.asStateFlow()

    private var _bars = MutableStateFlow(BarPeriod())
    val bars = _bars.asStateFlow()

    private val defaultTradeFormData = TradeFormData(
        date = LocalDate.now(zoneIdProvider.getTimeZone()),
        time = LocalTime.now(zoneIdProvider.getTimeZone()),
    )
    private var _currentTrade = MutableStateFlow(defaultTradeFormData)
    val currentTrade = _currentTrade.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val trades: StateFlow<List<Trade>> = combine(
        _companyInfo.filterNotNull(),
        _userIdFlow
    ) { companyInfo, uid -> companyInfo.symbol to uid }
    .flatMapLatest { (symbol, uid) ->
        if (uid == null) flowOf(emptyList())
        else database.tradeDao().getAllTradesOfSymbol(symbol, uid).asFlow()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private var _chartLoadState = MutableStateFlow(ChartLoadState())
    val loadState = _chartLoadState.asStateFlow()

    fun load(symbol: String) {
        loadDetails(symbol)
        loadSnapshot(symbol)
        loadBars(symbol)
    }

    private fun loadDetails(symbol: String) {
        alpacaDetailsDatasource.getStockOverview(symbol, onSuccess = {
            viewModelScope.launch {
                _companyInfo.emit(CompanyInfo(it.name, symbol))
                _chartLoadState.emit(_chartLoadState.value.copy(detailsLoaded = true))
            }
        }, onFail = {}, loadingFinished = {})
    }

    private fun loadSnapshot(symbol: String) {
        alpacaBarDatasource.getSnapshotFromSymbols(listOf(symbol), onSuccess = {
            val symbolsSnapshot: SnapshotResponse = it[symbol]!!

            viewModelScope.launch {
                val latestPriceFetched = LatestPrice.fromSnapshotResponse(symbolsSnapshot)
                if (!_latestPrice.value.isLoaded()) {
                    _currentTrade.emit(_currentTrade.value.copy(price = latestPriceFetched.value.toString()))
                }
                _latestPrice.emit(latestPriceFetched)

                _chartLoadState.emit(_chartLoadState.value.copy(snapshotLoaded = true))
            }
        }, onFail = {}, loadingFinished = {})
    }

    private fun loadBars(symbol: String) {
        alpacaBarDatasource.getBarsFromSymbol(symbol, onSuccess = {
            viewModelScope.launch {
                _bars.emit(BarPeriod.fromBarResponse(it))
                _chartLoadState.emit(_chartLoadState.value.copy(barsLoaded = true))
            }
        }, onFail = {}, loadingFinished = {})
    }

    fun toggleFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            val uid = auth.currentUser?.uid ?: return@launch
            val symbol = _companyInfo.value.symbol

            if (isFavorite) database.favoriteStockDao().deleteFavoriteStock(symbol, uid)
            else database.favoriteStockDao().insert(FavoriteStock(uid, symbol))
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
            val trade = _currentTrade.value
            database.tradeDao().insert(
                Trade(
                    auth.currentUser?.uid ?: return@launch,
                    _companyInfo.value.symbol,
                    trade.type,
                    trade.amount,
                    trade.price,
                    LocalDateTime.of(trade.date, trade.time)
                )
            )

            _currentTrade.emit(
                defaultTradeFormData.copy(price = _latestPrice.value.value.toString())
            )
            _showTradeCreationModal.emit(false)
        }
    }
}

