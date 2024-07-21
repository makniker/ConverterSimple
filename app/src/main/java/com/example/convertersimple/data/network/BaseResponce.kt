package com.example.convertersimple.data.network

import com.google.gson.annotations.SerializedName


class BaseResponse<T>(@SerializedName("data") val data: T)

typealias CurrenciesList = BaseResponse<Map<String, CurrencyInfo>>
typealias ExchangeRate = BaseResponse<Map<String, Double>>

data class CurrencyInfo(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("symbol_native") val symbolNative: String,
    @SerializedName("decimal_digits") val decimalDigits: Int,
    @SerializedName("rounding") val rounding: Int,
    @SerializedName("code") val code: String,
    @SerializedName("name_plural") val namePlural: String,
    @SerializedName("type") val type: String
)
