package com.lampjuice.budgetlight.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE users_new (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    login TEXT NOT NULL,
                    name TEXT NOT NULL,
                )
            """.trimIndent()
        )
        db.execSQL(
            """
                INSERT INTO users_new (
                    id,
                    login,
                    name
                )
                SELECT
                    id,
                    login,
                    name
                FROM users
            """.trimIndent()
        )

        db.execSQL(
            """
                DROP TABLE users
            """.trimIndent()
        )

        db.execSQL(
            """
                ALTER TABLE users_new RENAME TO users
            """.trimIndent()
        )

        db.execSQL(
            """
                CREATE UNIQUE INDEX index_users_login
                ON users (login)
            """.trimIndent()
        )

    }
}
