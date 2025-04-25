package com.example.stockgazer.data.repository

import com.example.stockgazer.domain.model.FollowedStockData

interface StockRepository {
    fun getFollowList(): List<FollowedStockData>
}