package com.example.convertersimple.ui

sealed interface UiState<out T> {
    data class Content<T>(val data: T) : UiState<T>
    data class Error<T>(val error: String) : UiState<T>
    class Loading<T> : UiState<T>
}