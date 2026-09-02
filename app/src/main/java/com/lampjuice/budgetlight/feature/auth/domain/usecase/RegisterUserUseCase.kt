package com.lampjuice.budgetlight.feature.auth.domain.usecase

import com.lampjuice.budgetlight.feature.auth.domain.error.AuthError
import com.lampjuice.budgetlight.feature.auth.domain.error.AuthException
import com.lampjuice.budgetlight.feature.auth.domain.model.User
import com.lampjuice.budgetlight.feature.auth.domain.repository.AuthRepository
import com.lampjuice.budgetlight.feature.auth.domain.repository.UserRepository
import com.lampjuice.budgetlight.feature.auth.domain.session.AuthSession
import com.lampjuice.budgetlight.feature.budget.domain.usecase.InitializeUserDataUseCase
import jakarta.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authSession: AuthSession,
    private val authRepository: AuthRepository,
    private val initializeUserDataUseCase: InitializeUserDataUseCase,
) {

    suspend operator fun invoke(
        login: String,
        name: String,
        password: String,
    ): Result<User> {
        val existingUser = userRepository.getUserByLogin(login)
        if (existingUser != null) {
            return Result.failure(
                AuthException(AuthError.UserAlreadyExists),
            )
        }

        return authRepository
            .register(
                login = login,
                name = name,
                password = password,
            )
            .mapCatching { result ->
                val localUser = userRepository.createUser(
                    login = result.user.login,
                    name = result.user.name,
                )
                initializeUserDataUseCase(localUser.id)

                authSession.setSession(
                    userId = localUser.id,
                    token = result.token
                )

                localUser
            }

    }
}
