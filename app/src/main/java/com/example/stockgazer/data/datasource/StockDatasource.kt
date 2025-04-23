package com.example.stockgazer.data.datasource

import com.example.stockgazer.data.response.TopMarketMoversResponse
import com.example.stockgazer.domain.model.CompanyProfile
import com.example.stockgazer.domain.model.StockMovement
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Header
import retrofit.http.Query

interface StockDatasource {
    @GET("screener/stocks/movers")
    fun getTopMarketMovers(
        @Header("APCA-API-KEY-ID") alpacaApiKeyId: String,
        @Header("APCA-API-SECRET-KEY") alpacaApiSecretKey: String,
        @Query("top") top: Int
    ): Call<TopMarketMoversResponse>

    @GET("screener/stocks/most-actives")
    fun getMostActiveStock(
        @Header("APCA-API-KEY-ID") alpacaApiKeyId: String,
        @Header("APCA-API-SECRET-KEY") alpacaApiSecretKey: String,
        @Query("by") by: String,
        @Query("top") top: Int,
    ): List<StockMovement>

    fun getCompanyProfile(symbol: String): CompanyProfile
}