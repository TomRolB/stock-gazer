package com.example.stockgazer.data.repository

import com.example.stockgazer.data.response.TopMarketMoversResponse
import com.example.stockgazer.domain.model.CompanyProfile
import com.example.stockgazer.domain.model.Stock
import com.example.stockgazer.domain.model.StockMovement

class FakeStockRepository : StockRepository {
    override fun getTopMarketMovers(): TopMarketMoversResponse {
        val topGainers = listOf(
            Stock(change = 4.87, percentChange = 342.96, price = 6.29, symbol = "AREB"),
            Stock(change = 0.7572, percentChange = 153.65, price = 1.25, symbol = "IBG"),
            Stock(change = 0.0161, percentChange = 140.0, price = 0.0276, symbol = "AREBW"),
            Stock(change = 0.945, percentChange = 97.93, price = 1.91, symbol = "SPWH"),
            Stock(change = 0.0279, percentChange = 90.0, price = 0.0589, symbol = "FAASW")
        )

        val topLosers = listOf(
            Stock(change = -7.624, percentChange = -95.66, price = 0.3455, symbol = "JYD"),
            Stock(change = -180.48, percentChange = -77.46, price = 52.52, symbol = "NMAX"),
            Stock(change = -1.14, percentChange = -71.7, price = 0.45, symbol = "RSLS"),
            Stock(change = -12.82, percentChange = -64.58, price = 7.03, symbol = "WTF"),
            Stock(change = -2.971, percentChange = -56.96, price = 2.245, symbol = "FLX")
        )

        return TopMarketMoversResponse(topGainers, topLosers)
    }

    override fun getMostActiveStock(): List<StockMovement> {
        return listOf(
            StockMovement(symbol = "TSLA", tradeCount = 2687475, volume = 211182979),
            StockMovement(symbol = "NVDA", tradeCount = 1695735, volume = 218563742),
            StockMovement(symbol = "PLTR", tradeCount = 783974, volume = 95963728),
            StockMovement(symbol = "TSLL", tradeCount = 762791, volume = 364517390),
            StockMovement(symbol = "TQQQ", tradeCount = 743316, volume = 120669919)
        )
    }

    override fun getCompanyProfile(symbol: String): CompanyProfile {
        val url = "https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/$symbol.png"
        return CompanyProfile(url)
    }
}