package com.example.convertersimple.features.convert

import com.example.convertersimple.data.CurrencyRepository
import com.example.convertersimple.data.RequestResult
import javax.inject.Inject

class FetchCurrenciesUseCase @Inject constructor(repository: CurrencyRepository) {
    suspend operator fun invoke(): RequestResult<List<String>> {
        TODO()
    }
}