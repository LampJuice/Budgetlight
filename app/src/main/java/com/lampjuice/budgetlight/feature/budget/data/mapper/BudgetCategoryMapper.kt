package com.lampjuice.budgetlight.feature.budget.data.mapper

import com.lampjuice.budgetlight.feature.budget.data.local.entity.BudgetCategoryEntity
import com.lampjuice.budgetlight.feature.budget.domain.model.BudgetCategory

fun BudgetCategoryEntity.toDomain() = BudgetCategory(
    id = id,
    budgetId = budgetId,
    categoryId = categoryId,
    plannedAmount = plannedAmount,
)

fun BudgetCategory.toEntity() = BudgetCategoryEntity(
    id = id,
    budgetId = budgetId,
    categoryId = categoryId,
    plannedAmount = plannedAmount,
)
