package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.Transaction
import com.lampjuice.budgetlight.domain.repository.TransactionRepository
import jakarta.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(transaction: Transaction) {
        transactionRepository.addTransaction(transaction)
    }
}
