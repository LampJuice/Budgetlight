package com.lampjuice.feature.auth.presentation.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthMeResponse(
    val userId: Long,
    val email: String,
    val name: String
)
