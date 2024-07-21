package com.example.convertersimple.features.convert.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ConverterUiState.Initial)
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    private val _convertState = MutableStateFlow(ResultUiState.Initial)
    val convertState: StateFlow<ResultUiState> = _convertState.asStateFlow()

    fun fetchCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ConverterUiState.Content(listOf("USD", "EUR", "RUB", "JPY", "GBP"))
        }
    }

    fun convertCurrency(sum: Double, base: String, exchange: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _convertState.value = ResultUiState.Content(
                sum = sum,
                convertedSum = sum * 2.5,
                base = base,
                exchange = exchange
            )
        }
    }
}