package com.wendychu.github_user_api.data.remote

import com.wendychu.github_user_api.data.Resource
import com.wendychu.github_user_api.data.remote.model.UserDetailResponse
import com.wendychu.github_user_api.data.remote.model.UserItemResponse
import com.wendychu.github_user_api.data.remote.model.UserSearchResultResponse
import com.wendychu.github_user_api.data.toDomainFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val gitHubService: GitHubServiceApi
): GithubRepository {

    override suspend fun searchUser(query: String): Flow<Resource<UserSearchResultResponse>> {
        return gitHubService.searchUser(query).toDomainFlow()
    }

    override suspend fun getUserDetail(username: String): Flow<Resource<UserDetailResponse>> {
        return gitHubService.getDetail(username).toDomainFlow()
    }
}