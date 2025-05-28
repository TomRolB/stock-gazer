package com.example.stockgazer.data.entities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TradeDao {
    @Insert
    suspend fun insert(favoriteStock: FavoriteStock)

    @Query("SELECT * FROM trade")
    fun getAllTrades(): LiveData<List<Trade>>
}

@Dao
interface FavoriteStockDao {
    @Insert
    suspend fun insert(favoriteStock: FavoriteStock)

    @Query("DELETE FROM favoritestock WHERE symbol = :symbol")
    suspend fun deleteFavoriteStock(symbol: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favoritestock WHERE symbol = :symbol)")
    fun isStockFavorite(symbol: String): LiveData<Boolean>

    @Query("SELECT symbol FROM favoritestock")
    fun getFavoriteStocks(): LiveData<List<String>>
}


