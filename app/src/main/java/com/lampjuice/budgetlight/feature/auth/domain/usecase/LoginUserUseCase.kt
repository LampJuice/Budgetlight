package com.lampjuice.budgetlight.feature.auth.domain.usecase

import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.auth.domain.repository.AuthRepository
import com.lampjuice.budgetlight.feature.auth.domain.session.AuthSession
import jakarta.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authSession: AuthSession,

    ) {
    suspend operator fun invoke(
        login: String,
        password: String,
    ): Result<User> =
        authRepository
            .login(
                login = login,
                password = password
            )
            .onSuccess { result ->
                authSession.setSession(
                    userId = result.user.id,
                    token = result.token
                )
            }
            .map { it.user }
}
