package com.lampjuice.budgetlight.data.mapper

import com.lampjuice.budgetlight.data.local.entity.BudgetCategoryEntity
import com.lampjuice.budgetlight.domain.model.BudgetCategory

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
