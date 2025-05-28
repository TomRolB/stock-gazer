package com.example.stockgazer.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stockgazer.R
import com.example.stockgazer.data.entities.FavoriteStock
import com.example.stockgazer.data.entities.FavoriteStockDao
import com.example.stockgazer.data.entities.Trade
import com.example.stockgazer.data.entities.TradeDao

@Database(entities = [FavoriteStock::class, Trade::class], version = 1)
abstract class StockGazerDatabase : RoomDatabase() {
    abstract fun tradeDao(): TradeDao
    abstract fun favoriteStockDao(): FavoriteStockDao

    companion object {
        @Volatile
        private var INSTANCE: StockGazerDatabase? = null

        fun getDatabase(context: Context): StockGazerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StockGazerDatabase::class.java,
                    context.getString(R.string.database_name)
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}