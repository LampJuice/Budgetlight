package com.lampjuice.feature.auth.presentation.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lampjuice.feature.auth.data.security.JwtConfig
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import org.koin.ktor.ext.get

private const val JWT_AUTH_NAME = "auth-jwt"

fun Application.configureAuthentication() {
    val jwtConfig = get<JwtConfig>()

    install(Authentication) {
        jwt(JWT_AUTH_NAME) {
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtConfig.secret))
                    .withIssuer(jwtConfig.issuer)
                    .withAudience(jwtConfig.audience)
                    .build()
            )

            validate { credential ->
                val userId = credential.payload
                    .getClaim("userId")
                    .asLong()

                val email = credential.payload
                    .getClaim("email")
                    .asString()

                if (userId != null && userId > 0 && !email.isNullOrBlank()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
