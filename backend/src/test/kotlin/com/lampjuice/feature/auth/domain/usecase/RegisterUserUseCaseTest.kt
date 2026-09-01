package com.lampjuice.feature.auth.domain.usecase

import com.lampjuice.feature.auth.domain.model.RegisterResult
import com.lampjuice.feature.auth.domain.model.User
import com.lampjuice.feature.auth.domain.repository.UserRepository
import com.lampjuice.feature.auth.domain.security.PasswordHasher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RegisterUserUseCaseTest {

    @Test
    fun `register creates user with hashed pass`() = runTest {
        val repository = FakeUserRepository()
        val passwordHasher = FakePasswordHasher()

        val useCase = RegisterUserUseCase(
            userRepository = repository,
            passwordHasher = passwordHasher
        )

        val result = useCase(
            email = "test@example.com",
            name = "test",
            password = "SuperSecretPass123!"
        )

        assertTrue(result is RegisterResult.Success)

        assertEquals(
            expected = "SuperSecretPass123!",
            actual = passwordHasher.receivedPassword
        )

        assertEquals(
            expected = "test@example.com",
            actual = repository.createdEmail
        )

        assertEquals(
            expected = "hashed-password",
            actual = repository.createdPasswordHash
        )

    }

    @Test
    fun `register returns email already exists when email already taken`() = runTest {
        val repository = FakeUserRepository(
            existingUser = User(
                id = 1,
                email = "test@example.com",
                name = "test",
                passwordHash = "hashed-password",
                createdAt = 123L

            )
        )
        val passwordHasher = FakePasswordHasher()
        val useCase = RegisterUserUseCase(
            userRepository = repository,
            passwordHasher = passwordHasher
        )

        val result = useCase(
            email = "test@example.com",
            name = "test",
            password = "SuperSecretPass123!"
        )

        assertEquals(
            RegisterResult.EmailAlreadyExists,
            result
        )
        assertEquals(
            expected = null,
            actual = passwordHasher.receivedPassword
        )

        assertEquals(
            expected = null,
            actual = repository.createdEmail
        )
    }

    private class FakeUserRepository(
        private val existingUser: User? = null
    ) : UserRepository {
        var createdEmail: String? = null
        var createdPasswordHash: String? = null

        override suspend fun createUser(
            email: String,
            name: String,
            passwordHash: String,
            createdAt: Long
        ): User {
            createdEmail = email
            createdPasswordHash = passwordHash
            return User(
                id = 1,
                email = email,
                name = name,
                passwordHash = passwordHash,
                createdAt = createdAt

            )
        }

        override suspend fun getUserByEmail(email: String): User? =
            existingUser?.takeIf { it.email == email }

    }

    private class FakePasswordHasher : PasswordHasher {

        var receivedPassword: String? = null

        override fun hash(password: String): String {
            receivedPassword = password
            return "hashed-password"
        }

        override fun verify(
            password: String,
            hash: String
        ): Boolean = false

    }
}

