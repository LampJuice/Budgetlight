package com.lampjuice.budgetlight.ui.mapper

import com.lampjuice.budgetlight.feature.budget.domain.model.BudgetCategoryInfo
import com.lampjuice.budgetlight.feature.budget.ui.home.model.CategoryBudgetUi

fun BudgetCategoryInfo.toUi(): CategoryBudgetUi = CategoryBudgetUi(
    id = categoryId,
    icon = icon,
    title = name,
    spent = spentAmount,
    limit = plannedAmount,
)
