package com.example.stockgazer.data.repository

import com.example.stockgazer.data.response.TopMarketMoversResponse
import com.example.stockgazer.domain.model.CompanyProfile
import com.example.stockgazer.domain.model.StockMovement

interface StockRepository {
    // TODO: I believe these shouldn't have "Response" on their names.
    //  If we followed the Clean Architecture, then the response is
    //  mapped to domain objects in the datasources.
    fun getTopMarketMovers(): TopMarketMoversResponse
    fun getMostActiveStock(): List<StockMovement>
    fun getCompanyProfile(symbol: String): CompanyProfile
}