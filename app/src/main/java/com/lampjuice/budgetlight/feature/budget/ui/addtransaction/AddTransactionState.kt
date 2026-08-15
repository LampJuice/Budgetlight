package com.lampjuice.budgetlight.feature.budget.ui.addtransaction

import com.lampjuice.budgetlight.feature.budget.domain.model.Category
import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionType
import java.time.LocalDate

data class AddTransactionState(
    val type: TransactionType = TransactionType.EXPENSE,
    val amount: String = "",
    val title: String = "",
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: Long? = null,
    val date: LocalDate = LocalDate.now(),
    val isSaving: Boolean = false,
)
