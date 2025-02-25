package com.example.convertersimple.features

import com.example.convertersimple.data.CurrencyRepository
import javax.inject.Inject

class FetchCurrenciesUseCase @Inject constructor(private val repository: CurrencyRepository) {
    suspend operator fun invoke(): Result<List<String>> {
        return repository.fetchCurrencyList().mapCatching { currency -> currency.map { it.code } }
    }
}