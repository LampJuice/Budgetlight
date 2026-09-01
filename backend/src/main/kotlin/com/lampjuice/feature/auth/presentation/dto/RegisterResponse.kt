package com.lampjuice.feature.auth.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val id: Long,
    val email: String,
)
