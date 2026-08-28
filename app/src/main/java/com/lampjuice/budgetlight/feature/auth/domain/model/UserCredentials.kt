package com.lampjuice.budgetlight.feature.auth.domain.model

data class UserCredentials(
    val user: User,
    val passwordHash: String,
)
