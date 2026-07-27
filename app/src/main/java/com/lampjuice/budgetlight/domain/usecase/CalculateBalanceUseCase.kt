package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.Balance
import com.lampjuice.budgetlight.domain.model.Transaction
import com.lampjuice.budgetlight.domain.model.TransactionType
import jakarta.inject.Inject

class CalculateBalanceUseCase @Inject constructor() {

    operator fun invoke(
        transactions: List<Transaction>,
    ): Balance {
        var income = 0L
        var expense = 0L

        transactions.forEach { transaction ->
            when (transaction.type) {
                TransactionType.INCOME -> income += transaction.amount
                TransactionType.EXPENSE -> expense += transaction.amount
            }
        }

        return Balance(
            total = income - expense,
            income = income,
            expense = expense,
        )
    }
}
