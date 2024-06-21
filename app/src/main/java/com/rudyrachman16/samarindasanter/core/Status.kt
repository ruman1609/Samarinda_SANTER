package com.rudyrachman16.samarindasanter.core

sealed class Status<out T> {
    data class Success<out T>(val data: T) : Status<T>()
    data object Loading: Status<Nothing>()
    data class Error(val error: Exception): Status<Nothing>()
}