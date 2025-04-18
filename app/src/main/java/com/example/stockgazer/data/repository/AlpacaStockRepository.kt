package com.example.stockgazer.data.repository

import com.example.stockgazer.data.datasource.AlpacaStockDatasource
import com.example.stockgazer.data.response.TopMarketMoversResponse
import com.example.stockgazer.domain.model.CompanyProfile
import com.example.stockgazer.domain.model.FollowedStockData
import com.example.stockgazer.domain.model.StockMovement
import javax.inject.Inject

class AlpacaStockRepository @Inject constructor(
    private val alpacaStockDatasource: AlpacaStockDatasource
) : StockRepository {
    override fun getTopMarketMovers(): TopMarketMoversResponse {
        TODO("Not yet implemented")
    }

    override fun getMostActiveStock(): List<StockMovement> {
        TODO("Not yet implemented")
    }

    override fun getFollowList(): List<FollowedStockData> {
        TODO("Not yet implemented")
    }

    override fun getCompanyProfile(symbol: String): CompanyProfile {
        TODO("Not yet implemented")
    }
}