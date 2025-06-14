package com.example.stockgazer.data.datasource

import com.example.stockgazer.data.response.AssetResponse
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Header
import retrofit.http.Query

interface AssetDatasource {
    @GET("v2/assets")
    fun getAssets(
        @Header("APCA-API-KEY-ID") alpacaApiKeyId: String,
        @Header("APCA-API-SECRET-KEY") alpacaApiSecretKey: String,
        @Query("status") status: String? = null,
        @Query("asset_class") assetClass: String? = null,
        @Query("exchange") exchange: String? = null,
        @Query("attributes") attributes: String? = null
    ): Call<List<AssetResponse>>
} 