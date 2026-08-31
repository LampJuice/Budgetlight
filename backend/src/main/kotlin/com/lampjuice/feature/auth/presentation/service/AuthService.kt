package com.lampjuice.feature.auth.presentation.service

import com.lampjuice.feature.auth.domain.model.RegisterResult
import com.lampjuice.feature.auth.domain.usecase.RegisterUserUseCase
import com.lampjuice.feature.auth.presentation.dto.RegisterRequest

class AuthService(
    private val registerUserUseCase: RegisterUserUseCase
) {
    suspend fun register(request: RegisterRequest): RegisterResult {
        return registerUserUseCase(
            email = request.email,
            password = request.password
        )
    }
}
