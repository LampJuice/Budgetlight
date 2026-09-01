package com.lampjuice.com.lampjuice.feature.auth.data.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lampjuice.com.lampjuice.feature.auth.domain.security.JwtService
import java.util.Date

class JwtServiceImpl(
    private val config: JwtConfig
) : JwtService {

private val algorithm = Algorithm.HMAC256(config.secret)

    override fun generateToken(
        userId: Long,
        email: String
    ): String =
        JWT.create()
            .withIssuer(config.issuer)
            .withAudience(config.audience)
            .withClaim("userId", userId)
            .withClaim("email", email)
            .withExpiresAt(
                Date(System.currentTimeMillis() + config.expirationMs)
            )
            .sign(algorithm)

}
