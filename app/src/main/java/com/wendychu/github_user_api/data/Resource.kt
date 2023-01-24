package com.wendychu.github_user_api.data

sealed class Resource<out T> {
    data class Success<T : Any>(val data: T) : Resource<T>() {
        fun <R : Any> mapResult(mapper: (inData: T) -> R): Resource<R> {
            return Success(mapper(data))
        }
    }

    data class Error(val message: String) : Resource<Nothing>()
}

fun <T, O : Any> Resource<T>.mapData(mapper: (inData: T) -> O): Resource<O> {
    return when (this) {
        is Resource.Success -> {
            this.mapResult {
                mapper(it)
            }
        }
        is Resource.Error -> this
    }
}