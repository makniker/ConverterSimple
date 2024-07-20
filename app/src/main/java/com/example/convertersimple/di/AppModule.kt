package com.example.convertersimple.di

import com.example.convertersimple.data.network.CurrencyApi
import com.example.convertersimple.data.network.KeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.freecurrencyapi.com/v1/"

    @Singleton
    @Provides
    fun provideCurrencyApi(): CurrencyApi {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).client(OkHttpClient.Builder().apply {
                addInterceptor(KeyInterceptor("fca_live_IYltIYuAvGsjIZjjycLEYbx504lWT5QBnO4PrLE3"))
            }.build()).build().create(CurrencyApi::class.java)
    }

}