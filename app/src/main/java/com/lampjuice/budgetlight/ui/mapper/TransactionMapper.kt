package com.lampjuice.budgetlight.ui.mapper

import com.lampjuice.budgetlight.domain.model.Transaction
import com.lampjuice.budgetlight.ui.home.TransactionUi

fun Transaction.toUi(): TransactionUi = TransactionUi(
    title = title,
    amount = amount,
    date = date.toString(),
    type = type,
    category = category,
)
