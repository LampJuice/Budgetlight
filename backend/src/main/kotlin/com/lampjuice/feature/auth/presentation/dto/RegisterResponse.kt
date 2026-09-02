package com.lampjuice.feature.auth.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val id: Long,
    val email: String,
    val name: String,
    val token: String,
)
