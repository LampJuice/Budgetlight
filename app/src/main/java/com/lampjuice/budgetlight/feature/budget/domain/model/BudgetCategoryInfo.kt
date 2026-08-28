package com.lampjuice.budgetlight.feature.budget.domain.model

data class BudgetCategoryInfo(
    val categoryId: Long,
    val name: String,
    val icon: CategoryIcon,
    val plannedAmount: Long,
    val spentAmount: Long,
)
