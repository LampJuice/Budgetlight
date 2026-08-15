package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.feature.budget.domain.model.Transaction
import com.lampjuice.budgetlight.feature.budget.domain.repository.TransactionRepository
import jakarta.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(transaction: Transaction) {
        transactionRepository.addTransaction(transaction)
    }
}
