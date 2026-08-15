package com.lampjuice.budgetlight.ui.home.model

data class BudgetSummaryUi(
    val id: Long,
    val expenseLimit: Long,
    val spent: Long,
    val remaining: Long,
    val progress: Float,
    val income: Long,
)
