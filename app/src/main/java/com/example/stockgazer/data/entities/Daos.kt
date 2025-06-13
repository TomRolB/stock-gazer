package com.example.stockgazer.data.entities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TradeDao {
    @Insert
    suspend fun insert(trade: Trade)

    @Query("SELECT * FROM trade WHERE symbol = :symbol AND userId = :userId")
    fun getAllTradesOfSymbol(symbol: String, userId: String): LiveData<List<Trade>>
}

@Dao
interface FavoriteStockDao {
    @Insert
    suspend fun insert(favoriteStock: FavoriteStock)

    @Query("DELETE FROM favoritestock WHERE symbol = :symbol AND userId = :userId")
    suspend fun deleteFavoriteStock(symbol: String, userId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favoritestock WHERE symbol = :symbol AND userId = :userId)")
    fun isStockFavorite(symbol: String, userId: String): LiveData<Boolean>

    @Query("SELECT symbol FROM favoritestock WHERE userId = :userId")
    fun getFavoriteStocks(userId: String): LiveData<List<String>>
}


