package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.feature.budget.domain.model.Transaction
import com.lampjuice.budgetlight.feature.budget.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveTransactionsUseCase
@Inject
constructor(
    private val repository: TransactionRepository,
) {
    operator fun invoke(accountId: Long): Flow<List<Transaction>> = repository.observeTransactions(accountId)
}
