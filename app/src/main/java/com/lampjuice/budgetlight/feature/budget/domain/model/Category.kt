package com.lampjuice.budgetlight.feature.budget.domain.model

data class Category(
    val id: Long,
    val name: String,
    val type: TransactionType,
    val icon: CategoryIcon,
    val isArchived: Boolean = false,
)
