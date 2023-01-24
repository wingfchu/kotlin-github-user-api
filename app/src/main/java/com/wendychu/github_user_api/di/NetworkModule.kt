package com.wendychu.github_user_api.di

import com.wendychu.github_user_api.BuildConfig
import com.wendychu.github_user_api.data.remote.GitHubServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BASE_URL = "https://api.github.com/"


    @Singleton
    @Provides
    fun providesHttpLogInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLogInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) addInterceptor(httpLogInterceptor)
            }
            .build()
    }

    private inline fun <reified T> createRestApiAdapter(okHttpClient: OkHttpClient, url: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(T::class.java)
    }

    @Singleton
    @Provides
    fun providesServices(okHttpClient: OkHttpClient): GitHubServiceApi {
        return createRestApiAdapter(okHttpClient, BASE_URL)
    }
}