package com.example.convertersimple.ui.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convertersimple.features.ConvertUseCase
import com.example.convertersimple.features.FetchCurrenciesUseCase
import com.example.convertersimple.ui.result.CurrencyUI
import com.example.convertersimple.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val convertUseCase: ConvertUseCase,
    private val fetchCurrenciesUseCase: FetchCurrenciesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ConverterUI>>(UiState.Loading())
    val uiState: StateFlow<UiState<ConverterUI>> = _uiState.asStateFlow()

    private val _convertState = MutableStateFlow<UiState<CurrencyUI>>(UiState.Loading())
    val convertState: StateFlow<UiState<CurrencyUI>> = _convertState.asStateFlow()

    fun fetchCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value =
                fetchCurrenciesUseCase().fold(
                    onSuccess = { UiState.Content(ConverterUI(it)) },
                    onFailure = { UiState.Error(it.message ?: "Something gone wrong") }
                )
        }
    }

    fun convertCurrency(sum: Double, base: String, exchange: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _convertState.value = convertUseCase(sum, base, exchange).fold(
                onSuccess = {
                    UiState.Content(
                        CurrencyUI(
                            sum, it, base, exchange
                        )
                    )
                },
                onFailure = { UiState.Error(it.message ?: "Something gone wrong") })
        }
    }
}
