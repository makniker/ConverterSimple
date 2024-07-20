package com.example.convertersimple.data

import com.example.convertersimple.data.network.CurrencyApi
import com.example.convertersimple.data.network.CurrencyInfo
import javax.inject.Inject

interface CurrencyRepository {
    fun fetchCurrencyList(): List<CurrencyInfo>
    suspend fun exchangeValue(sum: Double, base: String, exchange: String): Double
}

class CurrencyRepositoryTestImpl : CurrencyRepository {
    override fun fetchCurrencyList(): List<CurrencyInfo> {
        TODO()
    }

    override suspend fun exchangeValue(sum: Double, base: String, exchange: String): Double {
        TODO("Not yet implemented")
    }

}

class CurrencyRepositoryImpl @Inject constructor(private val api: CurrencyApi) :
    CurrencyRepository {
    override fun fetchCurrencyList(): List<CurrencyInfo> {
        TODO()
    }

    override suspend fun exchangeValue(sum: Double, base: String, exchange: String): Double {
        val result = api.getExchangeRate(base, exchange)
        result.also {
            if (it.isSuccess) {
            } else if (it.isFailure) {

            }
        }
        return sum
    }

}