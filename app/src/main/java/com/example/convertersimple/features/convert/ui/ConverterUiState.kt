package com.example.convertersimple.features.convert.ui

sealed interface ConverterUiState {

    class Content() : ConverterUiState

    data class Error(val error: String, ) : ConverterUiState

    data object Loading : ConverterUiState

    companion object {
        val Initial : ConverterUiState = Loading
    }
}