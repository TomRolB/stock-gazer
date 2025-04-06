package com.example.stockgazer.data.repository

import com.example.stockgazer.data.response.BarsResponse

interface BarRepository {
    fun getBarsForSymbol(symbol: String): BarsResponse
}