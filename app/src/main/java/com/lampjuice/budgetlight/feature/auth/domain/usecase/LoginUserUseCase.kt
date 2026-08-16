package com.lampjuice.budgetlight.feature.auth.domain.usecase

import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.auth.domain.repository.UserRepository
import com.lampjuice.budgetlight.feature.auth.domain.security.PasswordHasher
import com.lampjuice.budgetlight.feature.auth.domain.session.AuthSession
import jakarta.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authSession: AuthSession,
    private val passwordHasher: PasswordHasher,

) {
    suspend operator fun invoke(
        login: String,
        password: String,
    ): Result<User> {
        val user = userRepository.getUserByLogin(login)
        val passwordHash = user?.let {
            userRepository.getPassHashByLogin(login)
        }
        val result = when {
            user == null -> {
                Result.failure(
                    IllegalArgumentException("Пользователь с логином $login не найден"),
                )
            }

            passwordHash == null -> {
                Result.failure(
                    IllegalArgumentException("Пароль пользователя не найден"),
                )
            }

            !passwordHasher.verify(password, passwordHash) -> {
                Result.failure(
                    IllegalArgumentException("Неверный пароль"),
                )
            }

            else -> {
                authSession.setUserId(user.id)
                Result.success(user)
            }
        }
        return result
    }
}
