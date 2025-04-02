package com.example.stockgazer.data.datasource

import com.example.stockgazer.data.response.TopMarketMoversResponse
import com.example.stockgazer.domain.model.CompanyProfile
import com.example.stockgazer.domain.model.StockMovement

interface StockDatasource {
    fun getTopMarketMovers(): TopMarketMoversResponse
    fun getMostActiveStock(): List<StockMovement>
    fun getCompanyProfile(symbol: String): CompanyProfile
}