package com.lampjuice.budgetlight.ui.home

import com.lampjuice.budgetlight.domain.model.Transaction

fun Transaction.toUi(): TransactionUi = TransactionUi(
    title = category,
    amount = amount,
    date = date.toString(),
    type = type,
)
