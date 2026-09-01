package com.lampjuice.di

import com.lampjuice.feature.auth.data.security.JwtConfig
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureDependencyInjection() {

    val jwtConfig = JwtConfig(
        secret = environment.config.property("jwt.secret").getString(),
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expirationMs = environment.config.property("jwt.expiration-ms").getString().toLong()
    )

    install(Koin) {
        modules(
            appModule(
                jwtConfig = jwtConfig
            )
        )
    }
}
