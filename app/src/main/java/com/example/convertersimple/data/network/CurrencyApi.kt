package com.example.convertersimple.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("currencies")
    suspend fun getCurrenciesList(): Result<CurrenciesList>

    @GET("latest")
    suspend fun getExchangeRate(
        @Query("base_currency") base: String,
        @Query("currencies") requestCurrency: String
    ): Result<ExchangeRate>
}