package com.lampjuice.budgetlight.feature.auth.domain.usecase

import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.auth.domain.repository.AuthRepository
import com.lampjuice.budgetlight.feature.auth.domain.repository.UserRepository
import com.lampjuice.budgetlight.feature.auth.domain.session.AuthSession
import jakarta.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val authSession: AuthSession,

) {
    suspend operator fun invoke(
        login: String,
        password: String,
    ): Result<User> = authRepository
        .login(
            login = login,
            password = password,
        )
        .mapCatching { result ->
            val localUser = userRepository.getUserByLogin(result.user.login)
                ?: userRepository.createUser(
                    login = result.user.login,
                    name = result.user.name,
                )
            authSession.setSession(
                userId = localUser.id,
                token = result.token,
            )
            localUser
        }
}
