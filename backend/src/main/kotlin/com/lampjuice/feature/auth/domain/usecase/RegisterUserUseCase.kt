package com.lampjuice.com.lampjuice.feature.auth.domain.usecase

import com.lampjuice.com.lampjuice.feature.auth.domain.model.RegisterResult
import com.lampjuice.com.lampjuice.feature.auth.domain.repository.UserRepository
import com.lampjuice.com.lampjuice.feature.auth.domain.security.PasswordHasher

class RegisterUserUseCase(
    private val userRepository: UserRepository,
    private val passwordHasher: PasswordHasher
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): RegisterResult {
        if (userRepository.getUserByEmail(email) != null) {
            return RegisterResult.EmailAlreadyExists
        }

        val passwordHash = passwordHasher.hash(password)

        val createdUser = userRepository.createUser(
            email = email,
            passwordHash = passwordHash,
            createdAt = System.currentTimeMillis()
        )

        return RegisterResult.Success(createdUser)
    }
}
