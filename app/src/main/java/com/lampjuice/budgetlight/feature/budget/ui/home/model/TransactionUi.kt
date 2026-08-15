package com.lampjuice.budgetlight.feature.budget.ui.home.model

import com.lampjuice.budgetlight.feature.budget.domain.model.CategoryIcon
import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionType
import java.time.LocalDate

data class TransactionUi(
    val id: Long,
    val title: String,
    val amount: Long,
    val date: LocalDate,
    val type: TransactionType,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
)
