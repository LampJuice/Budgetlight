package com.lampjuice.feature.auth.domain.security

interface JwtService {
    fun generateToken(
        userId: Long,
        email: String,
        name: String
    ): String
}
