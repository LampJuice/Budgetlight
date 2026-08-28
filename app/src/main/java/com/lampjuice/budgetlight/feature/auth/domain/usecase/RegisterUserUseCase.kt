package com.lampjuice.budgetlight.feature.auth.domain.usecase

import com.lampjuice.budgetlight.feature.auth.domain.error.AuthError
import com.lampjuice.budgetlight.feature.auth.domain.error.AuthException
import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.auth.domain.repository.UserRepository
import com.lampjuice.budgetlight.feature.auth.domain.security.PasswordHasher
import com.lampjuice.budgetlight.feature.auth.domain.session.AuthSession
import com.lampjuice.budgetlight.feature.budget.domain.usecase.InitializeUserDataUseCase
import jakarta.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authSession: AuthSession,
    private val passwordHasher: PasswordHasher,
    private val initializeUserDataUseCase: InitializeUserDataUseCase,
) {

    suspend operator fun invoke(
        login: String,
        name: String,
        password: String,
    ): Result<User> {
        if (userRepository.getUserByLogin(login) != null) {
            return Result.failure(
                AuthException(AuthError.UserAlreadyExists),
            )
        }

        val passwordHash = passwordHasher.hash(password)

        val user = userRepository.createUser(
            login = login,
            name = name,
            passwordHash = passwordHash,
        )
        initializeUserDataUseCase(user.id)

        authSession.setUserId(user.id)

        return Result.success(user)
    }
}
