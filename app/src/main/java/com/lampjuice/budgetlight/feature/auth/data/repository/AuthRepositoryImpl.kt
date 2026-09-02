package com.lampjuice.budgetlight.feature.auth.data.repository

import com.lampjuice.budgetlight.feature.auth.data.remote.AuthApi
import com.lampjuice.budgetlight.feature.auth.data.remote.LoginRequestDto
import com.lampjuice.budgetlight.feature.auth.data.remote.RegisterRequestDto
import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.auth.domain.repository.AuthRepository
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun register(
        login: String,
        name: String,
        password: String
    ): Result<User> =
        runCatching {
            val response = authApi.register(
                RegisterRequestDto(
                    email = login,
                    name = name,
                    password = password
                )
            )
            User(
                id = response.id,
                login = response.email,
                name = name
            )
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

        }
}
