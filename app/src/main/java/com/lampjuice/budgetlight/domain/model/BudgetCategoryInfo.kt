package com.lampjuice.budgetlight.domain.model

data class BudgetCategoryInfo(
    val categoryId: Long,
    val name: String,
    val icon: CategoryIcon,
    val plannedAmount: Long,
    val spentAmount: Long,
)
