package com.lampjuice.budgetlight.domain.model

data class Category(
    val id: Long,
    val name: String,
    val type: TransactionType,
    val icon: CategoryIcon,
    val isArchived: Boolean = false,
)
