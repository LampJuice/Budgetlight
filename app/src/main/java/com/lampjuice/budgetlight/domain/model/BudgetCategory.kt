package com.lampjuice.budgetlight.domain.model

data class BudgetCategory(
    val id: Long,
    val budgetId: Long,
    val categoryId: Long,
    val plannedAmount: Long,
)
