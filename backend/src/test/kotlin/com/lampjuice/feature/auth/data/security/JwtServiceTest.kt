package com.lampjuice.feature.auth.data.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours

class JwtServiceTest {
    private val config = JwtConfig(
        secret = "test-secret-key",
        issuer = "budgetlight-test",
        audience = "budgetlight-client",
        expirationMs = 1.hours.inWholeMilliseconds,
    )

    private val jwtService = JwtServiceImpl(config)

    @Test
    fun `generateToken returns valid jwt`() {
        val token = jwtService.generateToken(
            userId = 42L,
            email = "test@example.com",
        )

        assertTrue(token.isNotBlank())

        val decoded = JWT.decode(token)

        assertNotNull(decoded)
        assertEquals("budgetlight-test", decoded.issuer)
        assertEquals(
            listOf("budgetlight-client"),
            decoded.audience,
        )
        assertEquals(42L, decoded.getClaim("userId").asLong())
        assertEquals(
            "test@example.com",
            decoded.getClaim("email").asString(),
        )
    }

    @Test
    fun `generateToken creates token with expiration`() {
        val token = jwtService.generateToken(
            userId = 42L,
            email = "test@example.com",
        )

        val decoded = JWT.decode(token)

        assertNotNull(decoded.expiresAt)
        assertTrue(decoded.expiresAt.time > System.currentTimeMillis())
    }

    @Test
    fun `generateToken signs token with configured secret`() {
        val token = jwtService.generateToken(
            userId = 42L,
            email = "test@example.com",
        )

        val verifier = JWT.require(
            Algorithm.HMAC256(config.secret)
        )
            .withIssuer(config.issuer)
            .withAudience(config.audience)
            .build()

        val verifiedToken = verifier.verify(token)

        assertEquals(42L, verifiedToken.getClaim("userId").asLong())
        assertEquals(
            "test@example.com",
            verifiedToken.getClaim("email").asString(),
        )
    }

    @Test
    fun `generateToken cannot be verified with another secret`() {
        val token = jwtService.generateToken(
            userId = 42L,
            email = "test@example.com",
        )

        val verifier = JWT.require(
            Algorithm.HMAC256("wrong-secret")
        )
            .withIssuer(config.issuer)
            .withAudience(config.audience)
            .build()

        assertFailsWith<com.auth0.jwt.exceptions.JWTVerificationException> {
            verifier.verify(token)
        }
    }
}
