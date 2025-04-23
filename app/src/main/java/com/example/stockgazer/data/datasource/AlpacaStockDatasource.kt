package com.example.stockgazer.data.datasource

import android.content.Context
import com.example.stockgazer.R
import com.example.stockgazer.data.response.MostActiveStockResponse
import com.example.stockgazer.data.response.TopMarketMoversResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

const val TOP_MARKET_MOVERS_LIMIT: Int = 30;

class AlpacaStockDatasource @Inject constructor(
    @ApplicationContext private val context: Context
)  {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(
            context.getString(R.string.v1beta1_url)
        )
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    private val datasource: StockDatasource = retrofit.create(StockDatasource::class.java)


    fun getTopMarketMovers(
        onSuccess: (TopMarketMoversResponse) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val alpacaApiKeyId: String = context.getString(R.string.APCA_API_KEY_ID)
        val alpacaApiSecretKey: String = context.getString(R.string.APCA_API_SECRET_KEY)
        val call: Call<TopMarketMoversResponse> = datasource.getTopMarketMovers(
            alpacaApiKeyId,
            alpacaApiSecretKey,
            TOP_MARKET_MOVERS_LIMIT
        )

        call.enqueue(object : Callback<TopMarketMoversResponse> {
            override fun onResponse(
                response: Response<TopMarketMoversResponse>?,
                retrofit: Retrofit?
            ) {
                loadingFinished()
                if(response?.isSuccess == true) {
                    val topMarketMovers: TopMarketMoversResponse = response.body()
                    onSuccess(topMarketMovers)
                } else {
                    onFailure(Exception("Bad request"))
                }
            }

            override fun onFailure(t: Throwable?) {
                println("Top Market Movers fetch failed: exception was $t")
                onFail()
            }
        })
    }

    fun getMostActiveStock (
        onSuccess: (MostActiveStockResponse) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val alpacaApiKeyId: String = context.getString(R.string.APCA_API_KEY_ID)
        val alpacaApiSecretKey: String = context.getString(R.string.APCA_API_SECRET_KEY)
        val call: Call<MostActiveStockResponse> = datasource.getMostActiveStock(
            alpacaApiKeyId,
            alpacaApiSecretKey,
            "trades",
            20
        )

        call.enqueue(object : Callback<MostActiveStockResponse> {
            override fun onResponse(
                response: Response<MostActiveStockResponse>?,
                retrofit: Retrofit?
            ) {
                loadingFinished()
                if(response?.isSuccess == true) {
                    val mostActiveStock: MostActiveStockResponse = response.body()
                    onSuccess(mostActiveStock)
                } else {
                    onFailure(Exception("Bad request"))
                }
            }

            override fun onFailure(t: Throwable?) {
                println("Most Active stock fetch failed: exception was $t")
                onFail()
            }
        })
    }

    fun getCompanyProfile(symbol: String) {
        TODO("Not yet implemented")
    }
}