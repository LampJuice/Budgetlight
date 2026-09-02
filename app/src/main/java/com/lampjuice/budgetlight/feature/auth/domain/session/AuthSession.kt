package com.lampjuice.budgetlight.feature.auth.domain.session

import kotlinx.coroutines.flow.Flow

interface AuthSession {
    val currentUserId: Flow<Long?>
    val token: Flow<String?>
    suspend fun setSession(userId: Long, token: String)
    suspend fun clear()
}
