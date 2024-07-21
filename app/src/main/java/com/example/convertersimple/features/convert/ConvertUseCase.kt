package com.example.convertersimple.features.convert

import com.example.convertersimple.data.CurrencyRepository
import javax.inject.Inject

class ConvertUseCase @Inject constructor(private val repository: CurrencyRepository) {
    suspend operator fun invoke(
        amount: Double, baseCurrency: String, exchangeCurrency: String
    ): Result<Double> = repository.exchangeValue(amount, baseCurrency, exchangeCurrency)

}