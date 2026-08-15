package com.lampjuice.budgetlight.feature.auth.domain.repository

import com.lampjuice.budgetlight.feature.auth.domain.model.User

interface UserRepository {
    suspend fun getUserByLogin(login: String): User?
    suspend fun createUser(
        login: String,
        name: String,
        passwordHash: String,
    ): User
    suspend fun getCurrentUser(): User?
}
