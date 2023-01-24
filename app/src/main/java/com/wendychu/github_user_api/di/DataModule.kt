package com.wendychu.github_user_api.di

import com.wendychu.github_user_api.data.remote.GithubRepository
import com.wendychu.github_user_api.data.remote.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindsGithubRepository(
        githubRepositoryImpl: GithubRepositoryImpl
    ): GithubRepository

}