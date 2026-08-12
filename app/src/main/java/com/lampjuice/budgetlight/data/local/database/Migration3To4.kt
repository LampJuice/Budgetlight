package com.lampjuice.budgetlight.data.local.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
                CREATE UNIQUE INDEX IF NOT EXISTS index_budgets_userId_year_month
                ON budgets (userId, year, month)
            """.trimIndent(),
        )
    }
}
