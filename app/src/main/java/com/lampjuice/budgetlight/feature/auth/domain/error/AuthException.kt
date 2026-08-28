package com.lampjuice.budgetlight.feature.auth.domain.error

class AuthException(
    val error: AuthError,
) : Exception()
