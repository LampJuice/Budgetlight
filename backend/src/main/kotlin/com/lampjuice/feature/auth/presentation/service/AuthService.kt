package com.lampjuice.feature.auth.presentation.service

import com.lampjuice.feature.auth.domain.model.LoginResult
import com.lampjuice.feature.auth.domain.model.RegisterResult
import com.lampjuice.feature.auth.domain.usecase.LoginUserUseCase
import com.lampjuice.feature.auth.domain.usecase.RegisterUserUseCase
import com.lampjuice.feature.auth.presentation.dto.LoginRequest
import com.lampjuice.feature.auth.presentation.dto.RegisterRequest

class AuthService(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase
) {
    suspend fun register(request: RegisterRequest): RegisterResult {
        return registerUserUseCase(
            email = request.email,
            password = request.password
        )
    }

    suspend fun login(request: LoginRequest): LoginResult {
        return loginUserUseCase(
            email = request.email,
            password = request.password
        )
    }
}
