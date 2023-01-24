package com.wendychu.github_user_api.data.remote

import com.wendychu.github_user_api.data.remote.model.UserSearchResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubServiceApi {
    @GET("search/users")
    suspend fun searchUser(@Query("q") query: String): Response<UserSearchResultResponse>
}