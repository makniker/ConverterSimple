package com.example.convertersimple.features.convert.ui

import java.util.Currency

sealed interface ConverterUiState {

    data class Content(val currency: List<String>) : ConverterUiState

    data class Error(val error: String, ) : ConverterUiState

    data object Loading : ConverterUiState

    companion object {
        val Initial : ConverterUiState = Loading
    }
}