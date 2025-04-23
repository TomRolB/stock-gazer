package com.example.stockgazer.data.datasource

import com.example.stockgazer.data.response.BarsResponse
import com.example.stockgazer.data.response.SnapshotResponse
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Header
import retrofit.http.Path
import retrofit.http.Query

interface BarDatasource {
    @GET("stocks/{symbol}/bars")
    fun getBarsForSymbol(
        @Header("APCA-API-KEY-ID") alpacaApiKeyId: String,
        @Header("APCA-API-SECRET-KEY") alpacaApiSecretKey: String,
        @Path("symbol") symbol: String,
        @Query("timeframe") timeframe: String = "1Hour",
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("limit") limit: Int = 20,
        @Query("sort") sort: String = "asc"
    ): Call<BarsResponse>

    @GET("stocks/snapshots")
    fun getSnapshotFromSymbol(
        @Header("APCA-API-KEY-ID") alpacaApiKeyId: String,
        @Header("APCA-API-SECRET-KEY") alpacaApiSecretKey: String,
        @Query("symbols") symbol: List<String>,
    ): Call<Map<String, SnapshotResponse>>
}