package com.example.convertersimple.data

import com.example.convertersimple.data.network.CurrencyApi
import com.example.convertersimple.data.network.CurrencyInfo
import javax.inject.Inject

interface CurrencyRepository {
    suspend fun fetchCurrencyList(): Result<List<CurrencyInfo>>
    suspend fun exchangeValue(sum: Double, base: String, exchange: String): Result<Double>
}

class CurrencyRepositoryImpl @Inject constructor(private val api: CurrencyApi) :
    CurrencyRepository {
    override suspend fun fetchCurrencyList(): Result<List<CurrencyInfo>> {
        return runCatching { api.getCurrenciesList().data.values.toList() }
    }

    override suspend fun exchangeValue(
        sum: Double, base: String, exchange: String
    ): Result<Double> {
        return runCatching {
            val r = api.getExchangeRate(base, exchange)
            r.data.values.toList()[0] * sum
        }
    }
}