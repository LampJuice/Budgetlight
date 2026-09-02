package com.lampjuice

import com.lampjuice.feature.auth.presentation.dto.AuthMeResponse
import com.lampjuice.feature.auth.presentation.routing.authRouting
import com.lampjuice.feature.auth.presentation.service.AuthService
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val authService by inject<AuthService>()

    routing {
        get("/health") {
            call.respond(
                mapOf(
                    "status" to "OK",
                )
            )
        }
        route("/auth") {
            authRouting(authService)

            authenticate("auth-jwt") {
                get("/me") {
                    val principal = call.principal<JWTPrincipal>()

                    val userId = principal
                        ?.payload
                        ?.getClaim("userId")
                        ?.asLong()

                    val email = principal
                        ?.payload
                        ?.getClaim("email")
                        ?.asString()

                    val name = principal
                        ?.payload
                        ?.getClaim("name")
                        ?.asString()

                    call.respond(
                        AuthMeResponse(
                            userId = userId
                                ?: error("User ID is missing in JWT"),
                            email = email
                                ?: error("Email is missing in JWT"),
                            name = name
                                ?: error("Name is missing in JWT")
                        )
                    )
                }
            }
        }
    }
}
