package com.lampjuice.feature.auth.data.repository

import com.lampjuice.feature.auth.data.database.UserTable
import com.lampjuice.feature.auth.data.database.UserTable.createdAt
import com.lampjuice.feature.auth.domain.model.User
import com.lampjuice.feature.auth.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class UserRepositoryImpl : UserRepository {
    override suspend fun createUser(
        email: String,
        name: String,
        passwordHash: String,
        createdAt: Long
    ): User = withContext(Dispatchers.IO) {
        transaction {
            val id = UserTable.insert {
                it[UserTable.email] = email
                it[UserTable.name] = name
                it[UserTable.passwordHash] = passwordHash
                it[UserTable.createdAt] = createdAt
            } get UserTable.id

            User(
                id = id.value,
                email = email,
                name = name,
                passwordHash = passwordHash,
                createdAt = createdAt
            )
        }
    }

    override suspend fun getUserByEmail(email: String): User?  =
        withContext(Dispatchers.IO){
            transaction {
                UserTable
                    .selectAll()
                    .where { UserTable.email eq email }
                    .singleOrNull()
                    ?.let {
                        User(
                            id = it[UserTable.id].value,
                            email = it[UserTable.email],
                            name = it[UserTable.name],
                            passwordHash = it[UserTable.passwordHash],
                            createdAt = it[createdAt]
                        )
                    }
            }
        }
}

