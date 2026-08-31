package com.lampjuice.com.lampjuice.feature.auth.data.repositrory

import com.lampjuice.com.lampjuice.database.DatabaseTestHelper
import com.lampjuice.com.lampjuice.feature.auth.data.database.UserTable
import com.lampjuice.com.lampjuice.feature.auth.data.repository.UserRepositoryImpl
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.v1.exceptions.ExposedSQLException
import org.jetbrains.exposed.v1.jdbc.deleteAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UserRepositoryTest {
    private val userRepository = UserRepositoryImpl()

    @BeforeTest
    fun sutUp() {
        DatabaseTestHelper.connect()
        DatabaseTestHelper.createSchema()
    }

    @AfterTest
    fun tearDown() {
        transaction {
            UserTable.deleteAll()
        }
    }

    @Test
    fun `create and find user`() = runTest {
        val created = userRepository.createUser(
            email = "test@example.com",
            passwordHash = "test-hash",
            createdAt = 123456789L,
        )

        assertNotNull(created.id)
        assertEquals("test@example.com", created.email)
        assertEquals("test-hash", created.passwordHash)

        val found = userRepository.getUserByEmail("test@example.com")

        assertNotNull(found)
        assertEquals(created.id, found.id)
        assertEquals(created.email, found.email)
    }

    @Test
    fun `findByEmail returns null for unknown email`() = runTest {
        val result = userRepository.getUserByEmail("unknown@example.com")
        assertNull(result)
    }

    @Test
    fun `create fails when email already exists`() = runTest {
        userRepository.createUser(
            email = "duplicate@example.com",
            passwordHash = "hash-1",
            createdAt = 123456789L,
        )

        assertFailsWith<ExposedSQLException> {
            userRepository.createUser(
                email = "duplicate@example.com",
                passwordHash = "hash-2",
                createdAt = 987654321L,
            )
        }
    }
}
