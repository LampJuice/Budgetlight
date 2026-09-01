package com.lampjuice.feature.auth.domain.model

data class User(
    val id: Long,
    val email: String,
    val name: String,
    val passwordHash: String,
    val createdAt: Long
)

