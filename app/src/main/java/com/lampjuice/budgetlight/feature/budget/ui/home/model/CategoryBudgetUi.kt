package com.lampjuice.budgetlight.feature.budget.ui.home.model

import com.lampjuice.budgetlight.feature.budget.domain.model.CategoryIcon

data class CategoryBudgetUi(
    val id: Long,
    val icon: CategoryIcon,
    val title: String,
    val spent: Long,
    val limit: Long,
) {
    val progress: Float
        get() = when {
            limit <= 0L -> 0f
            else -> (spent.toFloat() / limit).coerceIn(0f, 1f)
        }
    val isOverBudget: Boolean
        get() = spent > limit
}
