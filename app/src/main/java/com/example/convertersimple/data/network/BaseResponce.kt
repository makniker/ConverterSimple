package com.example.convertersimple.data.network

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(@SerializedName("data") val data: T)

typealias CurrenciesList = BaseResponse<HashMap<String, CurrencyInfo>>
typealias ExchangeRate = BaseResponse<HashMap<String, Double>>

data class CurrencyInfo(
    @SerializedName("code") val code: String,
    @SerializedName("name_plural") val namePlural: String,
)
