package com.lampjuice.feature.auth.domain.repository

import com.lampjuice.feature.auth.domain.model.User

interface UserRepository {

    suspend fun createUser(
        email: String,
        name: String,
        passwordHash: String,
        createdAt: Long
    ): User

    suspend fun getUserByEmail(email: String): User?

}

