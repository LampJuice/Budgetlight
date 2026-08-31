package com.lampjuice.com.lampjuice.feature.auth.domain.security

interface PasswordHasher {

    fun hash(password: String): String

    fun verify(password: String, hash: String): Boolean

}
