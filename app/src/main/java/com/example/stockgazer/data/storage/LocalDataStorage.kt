package com.example.stockgazer.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.stockgazer.data.storage.PreferencesKeys.FAVORITE_STOCK_SYMBOLS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore(name = "STOCKGAZER_DATA_STORE")

object PreferencesKeys {
    val FAVORITE_STOCK_SYMBOLS = stringSetPreferencesKey("favorite_stock_symbols")
}

suspend fun addFavoriteSymbol(context: Context, symbol: String) {
    context.dataStore.edit { preferences ->
        val currentFavorites = preferences[FAVORITE_STOCK_SYMBOLS] ?: emptySet()
        preferences[FAVORITE_STOCK_SYMBOLS] = currentFavorites + symbol
    }
}

suspend fun removeFavoriteSymbol(context: Context, symbol: String) {
    context.dataStore.edit { preferences ->
        val currentFavorites = preferences[FAVORITE_STOCK_SYMBOLS] ?: emptySet()
        preferences[FAVORITE_STOCK_SYMBOLS] = currentFavorites - symbol
    }
}

fun isSymbolFavorite(context: Context, symbol: String): Flow<Boolean> {
    return context.dataStore.data.map { preferences ->
        preferences[FAVORITE_STOCK_SYMBOLS]?.contains(symbol) ?: false
    }
}

fun getFavoriteSymbols(context: Context): Flow<Set<String>> {
    return context.dataStore.data.map { preferences ->
        preferences[FAVORITE_STOCK_SYMBOLS] ?: emptySet()
    }
}
