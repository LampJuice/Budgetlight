package com.lampjuice.feature.auth.data.security

data class JwtConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val expirationMs: Long
)
