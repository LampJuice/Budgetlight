package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.feature.budget.domain.model.CategoryIcon
import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionInfo
import com.lampjuice.budgetlight.feature.budget.domain.repository.CategoryRepository
import com.lampjuice.budgetlight.feature.budget.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ObserveTransactionInfoUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(accountId: Long): Flow<List<TransactionInfo>> = combine(
        transactionRepository.observeTransactions(accountId = accountId),
        categoryRepository.observeAllCategories(),
    ) { transactions, categories ->
        transactions.map { transaction ->
            val category = categories.find { it.id == transaction.categoryId }

            TransactionInfo(
                id = transaction.id,
                title = transaction.title,
                amount = transaction.amount,
                date = transaction.date,
                type = transaction.type,
                categoryName = category?.name ?: "Без категории",
                categoryIcon = category?.icon ?: CategoryIcon.OTHER,
            )
        }
    }
}
