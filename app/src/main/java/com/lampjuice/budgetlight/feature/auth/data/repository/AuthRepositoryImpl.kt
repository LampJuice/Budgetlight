package com.lampjuice.budgetlight.feature.auth.data.repository

import com.lampjuice.budgetlight.feature.auth.data.remote.AuthApi
import com.lampjuice.budgetlight.feature.auth.data.remote.LoginRequestDto
import com.lampjuice.budgetlight.feature.auth.data.remote.RegisterRequestDto
import com.lampjuice.budgetlight.feature.auth.domain.error.AuthError
import com.lampjuice.budgetlight.feature.auth.domain.error.AuthException
import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.auth.domain.repository.AuthRepository
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun register(
        login: String,
        name: String,
        password: String
    ): Result<AuthRepository.AuthResult> =
        runCatching {
            val response = authApi.register(
                RegisterRequestDto(
                    email = login,
                    name = name,
                    password = password
                )
            )
            AuthRepository.AuthResult(
                user = User(
                    id = response.id,
                    login = response.email,
                    name = response.name
                ),
                token = response.token
            )
        }.recoverCatching { error ->
            if (
                error is ClientRequestException &&
                error.response.status == HttpStatusCode.Conflict
            ) {
                throw AuthException(AuthError.UserAlreadyExists)
            }
            throw error
        }

    override suspend fun login(
        login: String,
        password: String
    ): Result<AuthRepository.AuthResult> =
        runCatching {
            val response = authApi.login(
                LoginRequestDto(
                    email = login,
                    password = password
                )
            )

            AuthRepository.AuthResult(
                user = User(
                    id = response.id,
                    login = response.email,
                    name = ""
                ),
                token = response.token
            )

        }.recoverCatching { error ->
            if (
                error is ClientRequestException &&
                error.response.status == HttpStatusCode.Unauthorized
            ) {
                throw AuthException(AuthError.InvalidCredentials)
            }
            throw error
        }
}
