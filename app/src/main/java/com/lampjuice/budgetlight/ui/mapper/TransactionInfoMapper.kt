package com.lampjuice.budgetlight.ui.mapper

import com.lampjuice.budgetlight.domain.model.TransactionInfo
import com.lampjuice.budgetlight.ui.home.model.TransactionUi

fun TransactionInfo.toUi(): TransactionUi = TransactionUi(
    title = title,
    amount = amount,
    date = date.toString(),
    type = type,
    categoryName = categoryName,
    categoryIcon = categoryIcon,
)
