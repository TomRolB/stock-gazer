package com.example.stockgazer.data.datasource

import com.example.stockgazer.data.response.DetailsResponse
import com.example.stockgazer.data.response.TopMarketMoversResponse
import com.example.stockgazer.domain.model.CompanyProfile
import com.example.stockgazer.domain.model.StockMovement
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Header
import retrofit.http.Path
import retrofit.http.Query

interface DetailsDatasource {
    @GET("/v2/assets/{symbol}")
    fun getStockOverview(
        @Header("APCA-API-KEY-ID") alpacaApiKeyId: String,
        @Header("APCA-API-SECRET-KEY") alpacaApiSecretKey: String,
        @Path("symbol") symbol: String
    ): Call<DetailsResponse>
}