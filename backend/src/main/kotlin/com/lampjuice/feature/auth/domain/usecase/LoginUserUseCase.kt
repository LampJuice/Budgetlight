package com.lampjuice.feature.auth.domain.usecase

import com.lampjuice.feature.auth.domain.model.LoginResult
import com.lampjuice.feature.auth.domain.repository.UserRepository
import com.lampjuice.feature.auth.domain.security.PasswordHasher

class LoginUserUseCase(
    private val userRepository: UserRepository,
    private val passwordHasher: PasswordHasher
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): LoginResult {
        val user = userRepository.getUserByEmail(email)
            ?: return LoginResult.InvalidCredentials

        if (!passwordHasher.verify(password, user.passwordHash)) {
            return LoginResult.InvalidCredentials
        }

        return LoginResult.Success(user)
    }

}
