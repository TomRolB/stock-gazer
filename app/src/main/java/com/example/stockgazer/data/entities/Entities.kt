package com.example.stockgazer.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.stockgazer.ui.screens.chart.TradeType
import java.time.LocalDateTime

@Entity
data class FavoriteStock(
    val symbol: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)

@Entity
@TypeConverters(Converters::class)
data class Trade(
    val symbol: String,
    val type: TradeType,
    val amount: String,
    val price: String,
    val datetime: LocalDateTime,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
