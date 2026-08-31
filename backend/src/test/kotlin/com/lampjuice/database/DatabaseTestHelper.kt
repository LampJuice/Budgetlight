package com.lampjuice.com.lampjuice.database

import com.lampjuice.com.lampjuice.feature.auth.data.database.UserTable
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseTestHelper {
    fun connect() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/budgetlight",
            user = "budgetlight",
            password = "budgetlight_dev"
        )
    }
    fun createSchema() {
        transaction {
            SchemaUtils.create(UserTable)
        }
    }
}
