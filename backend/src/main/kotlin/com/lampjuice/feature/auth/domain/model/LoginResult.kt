package com.lampjuice.feature.auth.domain.model

sealed interface LoginResult {
    data class Success(val user: User) : LoginResult
    object InvalidCredentials : LoginResult
}
