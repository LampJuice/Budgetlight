package com.lampjuice.budgetlight.feature.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val id: Long,
    val email: String,
    val name: String,
    val token: String,
)
