package com.example.convertersimple.data.network

import okhttp3.Interceptor
import okhttp3.Response


class KeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("apikey", "fca_live_IYltIYuAvGsjIZjjycLEYbx504lWT5QBnO4PrLE3")
            .build()
        return chain.proceed(modifiedRequest)
    }
}