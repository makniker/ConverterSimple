package com.example.convertersimple.features.convert.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convertersimple.data.RequestResult
import com.example.convertersimple.features.convert.ConvertUseCase
import com.example.convertersimple.features.convert.FetchCurrenciesUseCase
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
    private val _uiState = MutableStateFlow(ConverterUiState.Initial)
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    private val _convertState = MutableStateFlow(ResultUiState.Initial)
    val convertState: StateFlow<ResultUiState> = _convertState.asStateFlow()

    fun fetchCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = when(val r = fetchCurrenciesUseCase()) {
                is RequestResult.Error -> ConverterUiState.Error(r.message)
                is RequestResult.Success -> ConverterUiState.Content(r.data)
            }
        }
    }

    fun convertCurrency(sum: Double, base: String, exchange: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _convertState.value = when(val r = convertUseCase(sum, base, exchange)) {
                is RequestResult.Error -> ResultUiState.Error(r.message)
                is RequestResult.Success -> ResultUiState.Content(sum, r.data, base, exchange)
            }
        }
    }
}