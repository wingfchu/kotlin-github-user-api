package com.wendychu.github_user_api.data.remote

import com.wendychu.github_user_api.data.remote.model.UserDetailResponse
import com.wendychu.github_user_api.data.remote.model.UserItemResponse
import com.wendychu.github_user_api.data.remote.model.UserRepoItemResponse
import com.wendychu.github_user_api.data.remote.model.UserSearchResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubServiceApi {
    @GET("search/users")
    suspend fun searchUser(@Query("q") query: String): Response<UserSearchResultResponse>

    @GET("/users/{user}")
    suspend fun getDetail(@Path("user") user: String): Response<UserDetailResponse>

    @GET("/users/{user}/following")
    suspend fun getFollowing(@Path("user") user: String): Response<List<UserItemResponse>>

    @GET("/users/{user}/followers")
    suspend fun getFollowers(@Path("user") user: String): Response<List<UserItemResponse>>

    @GET("/users/{user}/repos")
    suspend fun getRepos(@Path("user") user: String): Response<List<UserRepoItemResponse>>
}