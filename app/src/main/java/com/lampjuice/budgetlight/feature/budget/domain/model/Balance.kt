package com.lampjuice.budgetlight.feature.budget.domain.model

data class Balance(
    val total: Long,
    val income: Long,
    val expense: Long,
)
