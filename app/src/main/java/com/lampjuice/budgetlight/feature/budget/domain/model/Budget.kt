package com.lampjuice.budgetlight.feature.budget.domain.model

data class Budget(
    val id: Long,
    val userId: Long,
    val year: Int,
    val month: Int,
    val plannedIncome: Long,
    val expenseLimit: Long,
)
