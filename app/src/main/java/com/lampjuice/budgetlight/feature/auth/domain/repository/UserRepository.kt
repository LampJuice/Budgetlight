package com.lampjuice.budgetlight.feature.auth.domain.repository

import com.lampjuice.budgetlight.feature.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserByLogin(login: String): User?

    suspend fun createUser(
        login: String,
        name: String,
    ): User
    suspend fun getCurrentUser(): User?

    fun observeUserById(userId: Long): Flow<User?>
}
