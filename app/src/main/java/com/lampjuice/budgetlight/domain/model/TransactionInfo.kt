package com.lampjuice.budgetlight.domain.model

import java.time.LocalDate

data class TransactionInfo(
    val id: Long,
    val title: String,
    val amount: Long,
    val date: LocalDate,
    val type: TransactionType,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
)
