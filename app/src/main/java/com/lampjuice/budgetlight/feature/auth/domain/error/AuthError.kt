package com.lampjuice.budgetlight.feature.auth.domain.error

sealed interface AuthError {
    data object UserAlreadyExists : AuthError
    data object InvalidCredentials : AuthError
    data object UserNotFound : AuthError
    data object PasswordNotFound : AuthError
    data object EmptyLogin : AuthError
    data object EmptyPassword : AuthError
    data object EmptyName : AuthError
    data object PasswordMismatch : AuthError
}
