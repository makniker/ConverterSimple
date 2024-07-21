package com.example.convertersimple.data.network

import okhttp3.Interceptor
import okhttp3.Response


class KeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val modifiedRequest = chain.request().newBuilder().addHeader("apikey", apiKey).build()
        return chain.proceed(modifiedRequest)
    }
}