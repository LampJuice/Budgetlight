package com.lampjuice.com.lampjuice.feature.auth.domain.model

data class User(
    val id: Long,
    val email: String,
    val passwordHash: String,
    val createdAt: Long
)
