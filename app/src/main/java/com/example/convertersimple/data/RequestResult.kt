package com.example.convertersimple.data

sealed interface RequestResult<E> {
    data class Success<E>(val data: E): RequestResult<E>
    data class Error<E>(val message: String): RequestResult<E>
}