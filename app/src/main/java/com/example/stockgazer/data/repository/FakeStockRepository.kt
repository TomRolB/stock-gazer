package com.example.stockgazer.data.repository

import com.example.stockgazer.domain.model.FollowedStockData

class FakeStockRepository : StockRepository {

    override fun getFollowList(): List<FollowedStockData> {
        return listOf(
            FollowedStockData(
                percentChange = 4.487,
                price = 6.29,
                symbol = "AAPL",
                name = "Apple Inc. Common Stock"
            ),
            FollowedStockData(
                percentChange = -2.7572,
                price = 1.25,
                symbol = "MSFT",
                name = "Microsoft Corporation Common Stock"
            ),
            FollowedStockData(
                percentChange = -3.0161,
                price = 0.0276,
                symbol = "LMT",
                name = "Lockheed Martin Corp."
            ),
            FollowedStockData(
                percentChange = 1.945,
                price = 1.91,
                symbol = "GGAL",
                name = "Grupo Financiero Galicia S.A. American Depositary Shares"
            ),
            FollowedStockData(
                percentChange = 0.0279,
                price = 0.0589,
                symbol = "MELI",
                name = "MercadoLibre, Inc. Common Stock"
            )
        )
    }
}