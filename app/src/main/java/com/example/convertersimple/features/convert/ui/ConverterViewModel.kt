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

    init {
        viewModelScope.launch(Dispatchers.IO) {  _uiState.value = ConverterUiState.Content() }
    }

    fun convertCurrency(sum: Double, base: String, exchange: String): Double {
        return 0.0
    }
}