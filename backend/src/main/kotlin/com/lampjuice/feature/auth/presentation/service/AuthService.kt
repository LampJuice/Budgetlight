package com.lampjuice.feature.auth.presentation.service

import com.lampjuice.com.lampjuice.feature.auth.presentation.model.RegisterServiceResult
import com.lampjuice.feature.auth.domain.model.LoginResult
import com.lampjuice.feature.auth.domain.model.RegisterResult
import com.lampjuice.feature.auth.domain.security.JwtService
import com.lampjuice.feature.auth.domain.usecase.LoginUserUseCase
import com.lampjuice.feature.auth.domain.usecase.RegisterUserUseCase
import com.lampjuice.feature.auth.presentation.dto.LoginRequest
import com.lampjuice.feature.auth.presentation.dto.LoginResponse
import com.lampjuice.feature.auth.presentation.dto.RegisterRequest
import com.lampjuice.feature.auth.presentation.dto.RegisterResponse
import com.lampjuice.feature.auth.presentation.model.LoginServiceResult

class AuthService(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val jwtService: JwtService
) {
    suspend fun register(request: RegisterRequest): RegisterServiceResult =
        when (
            val result = registerUserUseCase(
                email = request.email,
                name = request.name,
                password = request.password
            )
        ) {
            is RegisterResult.Success -> {
                val token = jwtService.generateToken(
                    userId = result.user.id,
                    email = result.user.email,
                    name = result.user.name

                )

                RegisterServiceResult.Success(
                    response = RegisterResponse(
                        id = result.user.id,
                        email = result.user.email,
                        name = result.user.name,
                        token = token
                    )
                )
            }

            RegisterResult.EmailAlreadyExists -> {
                RegisterServiceResult.EmailAlreadyExists
            }
        }


    suspend fun login(request: LoginRequest): LoginServiceResult =
        when (
            val result = loginUserUseCase(
                email = request.email,
                password = request.password
            )
        ) {
            is LoginResult.Success -> {
                val token = jwtService.generateToken(
                    userId = result.user.id,
                    email = result.user.email,
                    name = result.user.name
                )
                LoginServiceResult.Success(
                    LoginResponse(
                        id = result.user.id,
                        email = result.user.email,
                        name = result.user.name,
                        token = token
                    )
                )
            }

            LoginResult.InvalidCredentials -> {
                LoginServiceResult.InvalidCredentials
            }
        }
}
