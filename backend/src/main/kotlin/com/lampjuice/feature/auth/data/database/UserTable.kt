package com.lampjuice.feature.auth.data.database

import org.jetbrains.exposed.v1.core.dao.id.LongIdTable

object UserTable : LongIdTable("users")  {
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val createdAt = long("created_at")

}

