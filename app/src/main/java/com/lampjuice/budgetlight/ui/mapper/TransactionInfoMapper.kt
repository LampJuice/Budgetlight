package com.lampjuice.budgetlight.ui.mapper

import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionInfo
import com.lampjuice.budgetlight.feature.budget.ui.home.model.TransactionUi

fun TransactionInfo.toUi(): TransactionUi = TransactionUi(
    id = id,
    title = title,
    amount = amount,
    date = date,
    type = type,
    categoryName = categoryName,
    categoryIcon = categoryIcon,
)
