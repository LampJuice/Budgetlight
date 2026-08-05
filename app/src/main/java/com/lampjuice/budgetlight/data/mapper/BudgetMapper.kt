package com.lampjuice.budgetlight.data.mapper

import com.lampjuice.budgetlight.data.local.entity.BudgetEntity
import com.lampjuice.budgetlight.domain.model.Budget

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
