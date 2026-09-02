package com.lampjuice.database

import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object UserTableMigration {
    fun migrate() {
        transaction {
            exec(
                """
                    ALTER TABLE users
                    ADD COLUMN IF NOT EXISTS name VARCHAR(255) NOT NULL DEFAULT ''
                """.trimIndent()
            )
            exec(
                """
                    ALTER TABLE users
                    ALTER COLUMN name DROP DEFAULT

                """.trimIndent()
            )
        }
    }
}
