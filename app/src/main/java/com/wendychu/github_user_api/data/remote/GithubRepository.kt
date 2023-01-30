package com.wendychu.github_user_api.data.remote

import com.wendychu.github_user_api.data.Resource
import com.wendychu.github_user_api.data.remote.model.UserDetailResponse
import com.wendychu.github_user_api.data.remote.model.UserItemResponse
import com.wendychu.github_user_api.data.remote.model.UserRepoItemResponse
import com.wendychu.github_user_api.data.remote.model.UserSearchResultResponse
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun searchUser(query: String): Flow<Resource<UserSearchResultResponse>>
    suspend fun getUserDetail(username: String): Flow<Resource<UserDetailResponse>>
    suspend fun getUserRepos(username: String): Flow<Resource<List<UserRepoItemResponse>>>
    suspend fun getUserFollowing(username: String): Flow<Resource<List<UserItemResponse>>>
    suspend fun getUserFollowers(username: String): Flow<Resource<List<UserItemResponse>>>
}