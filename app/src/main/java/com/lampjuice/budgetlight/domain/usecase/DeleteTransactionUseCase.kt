package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.repository.TransactionRepository
import jakarta.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteTransaction(id)
    }
}
