package com.lampjuice.budgetlight.feature.budget.data.mapper

import com.lampjuice.budgetlight.feature.budget.data.local.entity.BudgetEntity
import com.lampjuice.budgetlight.feature.budget.domain.model.Budget

fun BudgetEntity.toDomain() = Budget(
    id = id,
    userId = userId,
    year = year,
    month = month,
    plannedIncome = plannedIncome,
    expenseLimit = expenseLimit,
)

fun Budget.toEntity() = BudgetEntity(
    id = id,
    userId = userId,
    year = year,
    month = month,
    plannedIncome = plannedIncome,
    expenseLimit = expenseLimit,
)
