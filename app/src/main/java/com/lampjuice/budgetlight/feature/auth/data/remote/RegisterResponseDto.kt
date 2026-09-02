package com.lampjuice.budgetlight.feature.auth.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val id: Long,
    val email: String,
    val name: String,
    val token: String
)
