package com.wendychu.github_user_api.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T : Any> Response<T>.toDomainFlow(): Flow<Resource<T>> {
    return flow {
        val apiCall = this@toDomainFlow
        if (apiCall.isSuccessful && apiCall.body() != null) {
            emit(Resource.Success(apiCall.body()!!))
        } else {
            emit(Resource.Error(apiCall.message()))
        }
    }
}