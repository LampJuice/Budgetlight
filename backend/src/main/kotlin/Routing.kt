package com.lampjuice

import com.lampjuice.feature.auth.presentation.routing.authRouting
import com.lampjuice.feature.auth.presentation.service.AuthService
import io.ktor.server.application.Application
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
        }
    }
}
