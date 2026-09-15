package ru.practicum.android.diploma.data.network

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()

    data class Error(
        val message: String? = null,
        val code: Int? = null,
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}
