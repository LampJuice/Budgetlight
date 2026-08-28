package com.lampjuice.budgetlight.feature.auth.domain.repository

import com.lampjuice.budgetlight.feature.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserByLogin(login: String): User?

    suspend fun getPassHashByLogin(login: String): String?
    suspend fun createUser(
        login: String,
        name: String,
        passwordHash: String,
    ): User
    suspend fun getCurrentUser(): User?

    fun observeUserById(userId: Long): Flow<User?>
}
