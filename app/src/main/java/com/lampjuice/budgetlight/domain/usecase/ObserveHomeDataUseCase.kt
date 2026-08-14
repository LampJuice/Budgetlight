package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.HomeData
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class ObserveHomeDataUseCase @Inject constructor(
    private val observeTransactionInfoUseCase: ObserveTransactionInfoUseCase,
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
    private val observeCurrentAccountUseCase: ObserveCurrentAccountUseCase,
    private val observeCurrentBudgetUseCase: ObserveCurrentBudgetUseCase,
    private val observeBudgetCategoryInfoUseCase: ObserveBudgetCategoryInfoUseCase,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(userId: Long): Flow<HomeData> {
        val accountFlow = observeCurrentAccountUseCase(userId)
        val budgetFlow = observeCurrentBudgetUseCase(userId)
        val transactionsFlow = accountFlow
            .flatMapLatest { account ->
                if (account == null) {
                    flowOf(emptyList())
                } else {
                    observeTransactionsUseCase(account.id)
                }
            }
        val transactionsInfoFlow = accountFlow
            .flatMapLatest { account ->
                if (account == null) {
                    flowOf(emptyList())
                } else {
                    observeTransactionInfoUseCase(account.id)
                }
            }
        val budgetCategoriesFlow = combine(
            budgetFlow,
            accountFlow,
        ) { budget, account ->
            budget to account
        }.flatMapLatest { (budget, account) ->
            if (budget == null || account == null) {
                flowOf(emptyList())
            } else {
                observeBudgetCategoryInfoUseCase(budget, account.id)
            }
        }

        return combine(
            budgetFlow,
            transactionsFlow,
            transactionsInfoFlow,
            budgetCategoriesFlow,
        ) { budget, transactions, transactionsInfo, categories ->
            HomeData(
                budget = budget,
                transactions = transactions,
                transactionsInfo = transactionsInfo,
                budgetCategories = categories,
            )
        }
    }
}
