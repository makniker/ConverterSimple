package com.example.convertersimple.data

import com.example.convertersimple.data.network.CurrencyInfo

interface CurrencyRepository {
    fun fetchCurrencyList(): List<CurrencyInfo>
    fun exchangeValue(sum: Double, base: String): Double
}