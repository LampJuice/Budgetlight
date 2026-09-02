package com.lampjuice.feature.auth.presentation.routing

import com.lampjuice.feature.auth.presentation.model.RegisterServiceResult
import com.lampjuice.feature.auth.presentation.dto.LoginRequest
import com.lampjuice.feature.auth.presentation.dto.RegisterRequest
import com.lampjuice.feature.auth.presentation.model.LoginServiceResult
import com.lampjuice.feature.auth.presentation.service.AuthService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.authRouting(
    authService: AuthService
) {
    post("/register") {
        val request = call.receive<RegisterRequest>()

        when (val result = authService.register(request)) {
            is RegisterServiceResult.Success -> {
                call.respond(
                    HttpStatusCode.Created,
                    result.response
                )
            }

            RegisterServiceResult.EmailAlreadyExists -> {
                call.respond(HttpStatusCode.Conflict)
            }
        }
    }

    post("/login") {
        val request = call.receive<LoginRequest>()

        when (val result = authService.login(request)) {
            is LoginServiceResult.Success -> {
                call.respond(
                    HttpStatusCode.OK,
                    result.response
                )
            }

            LoginServiceResult.InvalidCredentials -> {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}
