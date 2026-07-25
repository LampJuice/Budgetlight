package com.lampjuice.budgetlight.domain.repository

import com.lampjuice.budgetlight.domain.model.User

interface UserRepository {
    suspend fun getUser(): User
}
