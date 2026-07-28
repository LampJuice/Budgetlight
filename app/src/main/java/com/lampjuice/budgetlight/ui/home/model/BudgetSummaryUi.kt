package com.lampjuice.budgetlight.ui.home.model

data class BudgetSummaryUi(
    val budget: String,
    val spent: String,
    val remaining: String,
    val progress: Float,
)
