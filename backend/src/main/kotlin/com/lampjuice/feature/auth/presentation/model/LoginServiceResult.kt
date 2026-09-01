package com.lampjuice.feature.auth.presentation.model

import com.lampjuice.feature.auth.presentation.dto.LoginResponse

sealed interface LoginServiceResult {
    data class Success(val response: LoginResponse) : LoginServiceResult
    data object InvalidCredentials : LoginServiceResult
}
