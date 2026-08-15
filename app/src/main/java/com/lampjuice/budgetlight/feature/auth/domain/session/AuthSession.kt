package com.lampjuice.budgetlight.feature.auth.domain.session

import kotlinx.coroutines.flow.Flow

interface AuthSession {
    val currentUserId: Flow<Long?>
    suspend fun setUserId(userId: Long)
    suspend fun clear()
}
