package com.wendychu.github_user_api.data.remote

import com.wendychu.github_user_api.data.Resource
import com.wendychu.github_user_api.data.remote.model.UserDetailResponse
import com.wendychu.github_user_api.data.remote.model.UserItemResponse
import com.wendychu.github_user_api.data.remote.model.UserRepoItemResponse
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

    override suspend fun getUserRepos(username: String): Flow<Resource<List<UserRepoItemResponse>>> {
        return gitHubService.getRepos(username).toDomainFlow()
    }

    override suspend fun getUserFollowing(username: String): Flow<Resource<List<UserItemResponse>>> {
        return gitHubService.getFollowing(username).toDomainFlow()
    }

    override suspend fun getUserFollowers(username: String): Flow<Resource<List<UserItemResponse>>> {
        return gitHubService.getFollowers(username).toDomainFlow()
    }
}