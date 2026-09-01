package com.lampjuice.budgetlight.di

import com.lampjuice.budgetlight.feature.auth.data.remote.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    @Provides
    @Singleton
    fun provideAuthApi(
        client: HttpClient
    ): AuthApi =
        AuthApi(
            client = client,
            baseUrl = BASE_URL
        )
}
