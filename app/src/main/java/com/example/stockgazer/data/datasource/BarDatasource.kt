package com.example.stockgazer.data.datasource

import com.example.stockgazer.data.response.BarsResponse

interface BarDatasource {
    fun getBarsForSymbol(symbol: String): BarsResponse
}