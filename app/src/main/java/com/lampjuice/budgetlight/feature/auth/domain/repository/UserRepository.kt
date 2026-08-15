package com.lampjuice.budgetlight.feature.auth.domain.repository

import com.lampjuice.budgetlight.feature.auth.domain.model.User

interface UserRepository {
    suspend fun getOrCreateUser(): User
}
