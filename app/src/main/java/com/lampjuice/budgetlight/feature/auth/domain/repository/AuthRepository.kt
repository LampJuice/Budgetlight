package com.lampjuice.budgetlight.feature.auth.domain.repository

import com.lampjuice.budgetlight.feature.auth.domain.model.User

interface AuthRepository {
    suspend fun register(
        login: String,
        name: String,
        password: String
    ): Result<AuthResult>

    suspend fun login(
        login: String,
        password: String
    ): Result<AuthResult>

    data class AuthResult(
        val user: User,
        val token: String
    )
}


