package com.lampjuice.feature.auth.presentation.service

import com.lampjuice.feature.auth.domain.model.LoginResult
import com.lampjuice.feature.auth.domain.model.RegisterResult
import com.lampjuice.feature.auth.domain.security.JwtService
import com.lampjuice.feature.auth.domain.usecase.LoginUserUseCase
import com.lampjuice.feature.auth.domain.usecase.RegisterUserUseCase
import com.lampjuice.feature.auth.presentation.dto.LoginRequest
import com.lampjuice.feature.auth.presentation.dto.LoginResponse
import com.lampjuice.feature.auth.presentation.dto.RegisterRequest
import com.lampjuice.feature.auth.presentation.model.LoginServiceResult

class AuthService(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val jwtService: JwtService
) {
    suspend fun register(request: RegisterRequest): RegisterResult {
        return registerUserUseCase(
            email = request.email,
            password = request.password
        )
    }

    suspend fun login(request: LoginRequest): LoginServiceResult {
        return when (
            val result = loginUserUseCase(
                email = request.email,
                password = request.password
            )
        ) {
            is LoginResult.Success -> {
                val token = jwtService.generateToken(
                    userId = result.user.id,
                    email = result.user.email
                )
                LoginServiceResult.Success(
                    LoginResponse(
                        id = result.user.id,
                        email = result.user.email,
                        token = token
                    )
                )
            }

            LoginResult.InvalidCredentials -> {
                LoginServiceResult.InvalidCredentials
            }
        }
    }
}
