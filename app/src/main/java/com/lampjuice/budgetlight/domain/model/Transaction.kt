package com.lampjuice.budgetlight.domain.model

import java.time.LocalDate

data class Transaction(
    val id: Long,
    val accountId: Long,
    val title: String,
    val amount: Long,
    val category: String,
    val date: LocalDate,
    val type: TransactionType,
)
