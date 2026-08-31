package com.lampjuice.feature.auth.domain.model

sealed interface RegisterResult {
    data class Success(
        val user: User
    ) : RegisterResult

    data object EmailAlreadyExists : RegisterResult
}

