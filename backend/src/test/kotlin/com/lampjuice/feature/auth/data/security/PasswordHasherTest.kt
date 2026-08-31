package com.lampjuice.feature.auth.data.security

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class PasswordHasherTest {

    private val passwordHasher = PasswordHasherImpl()

    @Test
    fun `hash does not return original password`() {
        val password = "SuperSecretPass123!"
        val hash = passwordHasher.hash(password)

        assertNotEquals(password, hash)
    }

    @Test
    fun `verify returns true for correct password`() {
        val password = "SuperSecretPass123!"
        val hash = passwordHasher.hash(password)

        val result = passwordHasher.verify(password, hash)

        assertTrue(result)
    }

    @Test
    fun `verify returns false for incorrect password`() {
        val password = "SuperSecretPass123!"
        val hash = passwordHasher.hash(password)

        val result = passwordHasher.verify("WrongPassword", hash)
        assertFalse(result)
    }
}

