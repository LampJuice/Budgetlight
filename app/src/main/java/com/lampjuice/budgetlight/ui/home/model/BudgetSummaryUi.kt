package com.lampjuice.budgetlight.ui.home.model

data class BudgetSummaryUi(
    val budget: Long,
    val spent: Long,
    val remaining: Long,
    val progress: Float,
)
