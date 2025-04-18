package com.example.stockgazer.data.datasource

import android.content.Context
import com.example.stockgazer.R
import com.example.stockgazer.data.response.BarsResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject


class AlpacaBarDatasource @Inject constructor(
    @ApplicationContext private val context: Context
)  {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(
            context.getString(R.string.v2_url)
        )
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    private val datasource: BarDatasource = retrofit.create(BarDatasource::class.java)

    private val alpacaApiKeyId: String = context.getString(R.string.APCA_API_KEY_ID)
    private val alpacaApiSecretKey: String = context.getString(R.string.APCA_API_SECRET_KEY)

    private val isoNow    = Instant.now().toString()
    private val iso5DaysAgo = Instant.now().minus(5, ChronoUnit.DAYS).toString()


    fun getBarsFromSymbol(
        symbol: String,
        onSuccess: (BarsResponse) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val call: Call<BarsResponse> = datasource.getBarsForSymbol(
            alpacaApiKeyId,
            alpacaApiSecretKey,
            symbol = "AAPL",
            start = iso5DaysAgo,
            end = isoNow
        )

        call.enqueue(object : Callback<BarsResponse> {
            override fun onResponse(response: Response<BarsResponse>?, retrofit: Retrofit?) {
                loadingFinished()
                if(response?.isSuccess == true) {
                    val bars: BarsResponse = response.body()
                    onSuccess(bars)
                } else {
                    onFailure(Exception("Bad request"))
                }
            }

            override fun onFailure(t: Throwable?) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getMostActiveStock() {
        TODO("Not yet implemented")
    }

    fun getCompanyProfile(symbol: String) {
        TODO("Not yet implemented")
    }
}