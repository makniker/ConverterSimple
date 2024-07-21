package com.example.convertersimple.di

import com.example.convertersimple.data.CurrencyRepository
import com.example.convertersimple.data.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Singleton
    @Binds
    abstract fun provideCurrencyRepository(impl: CurrencyRepositoryImpl): CurrencyRepository
}