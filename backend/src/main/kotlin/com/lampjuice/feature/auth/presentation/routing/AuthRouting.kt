package com.lampjuice.feature.auth.presentation.routing

import com.lampjuice.feature.auth.domain.model.LoginResult
import com.lampjuice.feature.auth.domain.model.RegisterResult
import com.lampjuice.feature.auth.presentation.dto.LoginRequest
import com.lampjuice.feature.auth.presentation.dto.LoginResponse
import com.lampjuice.feature.auth.presentation.dto.RegisterRequest
import com.lampjuice.feature.auth.presentation.dto.RegisterResponse
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
            is RegisterResult.Success -> {
                call.respond(
                    HttpStatusCode.Created,
                    RegisterResponse(
                        id = result.user.id,
                        email = result.user.email
                    ),
                )
            }

            RegisterResult.EmailAlreadyExists -> {
                call.respond(HttpStatusCode.Conflict)
            }
        }
    }

    post("/login") {
        val request = call.receive<LoginRequest>()

        when (val result = authService.login(request)) {
            is LoginResult.Success -> {
                call.respond(
                    HttpStatusCode.OK,
                    LoginResponse(
                        id = result.user.id,
                        email = result.user.email
                    )
                )
            }

            LoginResult.InvalidCredentials -> {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}
