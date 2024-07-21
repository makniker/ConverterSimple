package com.example.convertersimple.features.convert.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convertersimple.features.convert.ConvertUseCase
import com.example.convertersimple.features.convert.FetchCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun fetchCurrencies() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _uiState.value =
                fetchCurrenciesUseCase().fold(onSuccess = { ConverterUiState.Content(it) },
                    onFailure = { ConverterUiState.Error(it.message ?: "Something gone wrong") })
        }
    }

    fun convertCurrency(sum: Double, base: String, exchange: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _convertState.value = convertUseCase(sum, base, exchange).fold(onSuccess = {
                ResultUiState.Content(
                    sum, it, base, exchange
                )
            }, onFailure = { ResultUiState.Error(it.message ?: "Something gone wrong") })
        }
    }
}
