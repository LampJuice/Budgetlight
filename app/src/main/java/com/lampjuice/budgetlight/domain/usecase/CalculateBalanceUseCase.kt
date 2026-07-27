package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.Balance
import com.lampjuice.budgetlight.domain.model.Transaction
import com.lampjuice.budgetlight.domain.model.TransactionType
import jakarta.inject.Inject

class CalculateBalanceUseCase @Inject constructor() {
    operator fun invoke(transactions: List<Transaction>): Balance {
        val income = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        val expense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
        val total = income - expense
        return Balance(total, income, expense)
    }
}
