package com.example.stockgazer.data.datasource

import android.content.Context
import com.example.stockgazer.R
import com.example.stockgazer.data.response.AssetResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

class AlpacaAssetsDatasource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(context.getString(R.string.paper_api_url))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val datasource: AssetDatasource = retrofit.create(AssetDatasource::class.java)

    fun getAllAssets(
        onSuccess: (List<String>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val alpacaApiKeyId: String = context.getString(R.string.APCA_API_KEY_ID)
        val alpacaApiSecretKey: String = context.getString(R.string.APCA_API_SECRET_KEY)
        val call: Call<List<AssetResponse>> = datasource.getAssets(
            alpacaApiKeyId,
            alpacaApiSecretKey,
            status = "active",
            assetClass = "us_equity"
        )
        call.enqueue(object : Callback<List<AssetResponse>> {
            override fun onResponse(
                response: Response<List<AssetResponse>>?,
                retrofit: Retrofit?
            ) {
                loadingFinished()
                if (response?.isSuccess == true) {
                    val assets: List<AssetResponse> = response.body()
                    onSuccess(assets.map { it.symbol })
                } else {
                    onFailure(Exception("Bad request"))
                }
            }

            override fun onFailure(t: Throwable?) {
                println("Could not fetch assets. Exception was $t")
                onFail()
            }
        })
    }
} 