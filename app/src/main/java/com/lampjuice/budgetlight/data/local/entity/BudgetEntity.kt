package com.lampjuice.budgetlight.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "budgets",
    indices = [
        Index(
            value = ["userId", "year", "month"],
            unique = true,
        ),
    ],
)
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val userId: Long,
    val year: Int,
    val month: Int,
    val plannedIncome: Long,
    val expenseLimit: Long,
)
