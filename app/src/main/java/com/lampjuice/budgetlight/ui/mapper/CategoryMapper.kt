package com.lampjuice.budgetlight.ui.mapper

import com.lampjuice.budgetlight.domain.model.Category
import com.lampjuice.budgetlight.ui.home.model.CategoryBudgetUi

fun Category.toUi(): CategoryBudgetUi = CategoryBudgetUi(
    id = id,
    title = name,
    spent = 0,
    limit = 0,
)
