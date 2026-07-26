package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.Transaction
import com.lampjuice.budgetlight.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveTransactionsUseCase
@Inject
constructor(
    private val repository: TransactionRepository,
) {
    operator fun invoke(accountId: Long): Flow<List<Transaction>> = repository.observeTransactions(accountId)
}
