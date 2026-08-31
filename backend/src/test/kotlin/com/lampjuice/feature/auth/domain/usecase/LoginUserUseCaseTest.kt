package com.lampjuice.feature.auth.domain.usecase

import com.lampjuice.feature.auth.domain.model.LoginResult
import com.lampjuice.feature.auth.domain.model.User
import com.lampjuice.feature.auth.domain.repository.UserRepository
import com.lampjuice.feature.auth.domain.security.PasswordHasher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class LoginUserUseCaseTest {

    private val user = User(
        id = 1L,
        email = "test@example.com",
        passwordHash = "hashed-correct-password",
        createdAt = 123456789L,
    )

    private val userRepository = object : UserRepository {

        override suspend fun createUser(
            email: String,
            passwordHash: String,
            createdAt: Long,
        ): User = user

        override suspend fun getUserByEmail(email: String): User? =
            if (email == user.email) user else null
    }

    private val passwordHasher = object : PasswordHasher {

        override fun hash(password: String): String =
            "hashed-$password"

        override fun verify(
            password: String,
            hash: String,
        ): Boolean =
            hash == "hashed-$password"
    }

    private val useCase = LoginUserUseCase(
        userRepository = userRepository,
        passwordHasher = passwordHasher,
    )

    @Test
    fun `returns success for valid credentials`() = runTest {
        val result = useCase(
            email = "test@example.com",
            password = "correct-password",
        )

        val success = assertIs<LoginResult.Success>(result)

        assertEquals(user, success.user)
    }

    @Test
    fun `returns invalid credentials for wrong password`() = runTest {
        val result = useCase(
            email = "test@example.com",
            password = "wrong-password",
        )

        assertEquals(
            LoginResult.InvalidCredentials,
            result,
        )
    }

    @Test
    fun `returns invalid credentials for unknown email`() = runTest {
        val result = useCase(
            email = "unknown@example.com",
            password = "correct-password",
        )

        assertEquals(
            LoginResult.InvalidCredentials,
            result,
        )
    }
}
