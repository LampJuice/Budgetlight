package com.lampjuice.com.lampjuice.feature.auth.data.security

import com.lampjuice.com.lampjuice.feature.auth.domain.security.PasswordHasher
import com.password4j.Argon2Function
import com.password4j.types.Argon2

class PasswordHasherImpl : PasswordHasher {

    private companion object {
        const val MEMORY_COST = 65536
        const val PARALLELISM = 1
        const val ITERATIONS = 3
        const val HASH_LENGTH = 32
        const val ARGON2_VERSION = 19
    }

    private val argon2 = Argon2Function.getInstance(
        MEMORY_COST,
        ITERATIONS,
        PARALLELISM,
        HASH_LENGTH,
        Argon2.ID,
        ARGON2_VERSION
    )

    override fun hash(password: String): String =
        argon2.hash(password).result


    override fun verify(
        password: String,
        hash: String
    ): Boolean =
        argon2.check(password, hash)
}
