package com.lampjuice.budgetlight.ui.mapper

import com.lampjuice.budgetlight.domain.model.BudgetCategoryInfo
import com.lampjuice.budgetlight.ui.home.model.CategoryBudgetUi

fun BudgetCategoryInfo.toUi(): CategoryBudgetUi = CategoryBudgetUi(
    id = categoryId,
    icon = icon.name,
    title = name,
    spent = spentAmount,
    limit = plannedAmount,
)
