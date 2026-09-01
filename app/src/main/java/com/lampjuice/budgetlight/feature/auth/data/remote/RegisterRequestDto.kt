package com.lampjuice.budgetlight.feature.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val email: String,
    val name: String,
    val password: String,
)
