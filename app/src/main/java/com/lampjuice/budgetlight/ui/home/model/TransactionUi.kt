package com.lampjuice.budgetlight.ui.home.model

import com.lampjuice.budgetlight.domain.model.CategoryIcon
import com.lampjuice.budgetlight.domain.model.TransactionType

data class TransactionUi(
    val id: Long,
    val title: String,
    val amount: Long,
    val date: String,
    val type: TransactionType,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
)
