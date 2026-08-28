package com.lampjuice.budgetlight.feature.auth.domain.usecase

import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.auth.domain.repository.UserRepository
import com.lampjuice.budgetlight.feature.auth.domain.session.AuthSession
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class ObserveCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authSession: AuthSession,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<User?> = authSession.currentUserId.flatMapLatest { userId ->
        if (userId == null) {
            flowOf(null)
        } else {
            userRepository.observeUserById(userId)
        }
    }
}
