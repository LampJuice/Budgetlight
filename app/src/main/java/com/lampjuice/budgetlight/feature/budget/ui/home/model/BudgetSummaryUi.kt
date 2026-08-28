package com.lampjuice.budgetlight.feature.budget.ui.home.model

data class BudgetSummaryUi(
    val id: Long,
    val expenseLimit: Long,
    val spent: Long,
    val remaining: Long,
    val progress: Float,
    val income: Long,
)
