package com.lampjuice.feature.auth.presentation.model

import com.lampjuice.feature.auth.presentation.dto.RegisterResponse

sealed interface RegisterServiceResult {
    data class Success(val response: RegisterResponse) : RegisterServiceResult
    object EmailAlreadyExists : RegisterServiceResult
}
