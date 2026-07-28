package com.lampjuice.budgetlight.ui.home.model

import com.lampjuice.budgetlight.domain.model.TransactionType

data class TransactionUi(
    val title: String,
    val category: String,
    val amount: Long,
    val date: String,
    val type: TransactionType,
)
