package com.example.convertersimple.features.convert.ui

sealed interface ResultUiState {
    data class Content(
        val sum: Double,
        val convertedSum: Double,
        val base: String,
        val exchange: String
    ) : ResultUiState

    data class Error(val error: String): ResultUiState

    data object Loading : ResultUiState

    companion object {
        val Initial: ResultUiState = Loading
    }
}