package com.lampjuice.budgetlight.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.domain.model.Budget
import com.lampjuice.budgetlight.domain.model.TransactionType
import com.lampjuice.budgetlight.domain.usecase.InitializeUserUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveBudgetCategoryInfoUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveCurrentAccountUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveCurrentBudgetUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveTransactionInfoUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveTransactionsUseCase
import com.lampjuice.budgetlight.domain.usecase.SaveCurrentBudgetUseCase
import com.lampjuice.budgetlight.ui.home.model.BudgetSummaryUi
import com.lampjuice.budgetlight.ui.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
    private val observeCurrentAccountUseCase: ObserveCurrentAccountUseCase,
    private val observeCurrentBudgetUseCase: ObserveCurrentBudgetUseCase,
    private val initializeUserUseCase: InitializeUserUseCase,
    private val observeBudgetCategoryInfoUseCase: ObserveBudgetCategoryInfoUseCase,
    private val observeTransactionInfoUseCase: ObserveTransactionInfoUseCase,
    private val saveCurrentBudgetUseCase: SaveCurrentBudgetUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        observeHomeData()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeHomeData() {
        viewModelScope.launch {
            val user = initializeUserUseCase()
            val budgetFlow = observeCurrentBudgetUseCase(user.id)

            val accountFlow = observeCurrentAccountUseCase(user.id)
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
                    observeBudgetCategoryInfoUseCase(budget.id, account.id)
                }
            }
            combine(
                budgetFlow,
                transactionsFlow,
                transactionsInfoFlow,
                budgetCategoriesFlow,

            ) { budget, transactions, transactionsInfo, categories ->
                val budgetSummary = budget?.let {
                    val monthlyExpenses = transactions
                        .asSequence()
                        .filter { transaction ->
                            transaction.date.year == budget.year &&
                                transaction.date.monthValue == budget.month &&
                                transaction.type == TransactionType.EXPENSE
                        }
                        .sumOf { it.amount }
                    createBudgetSummary(
                        budget = budget,
                        expense = monthlyExpenses,

                    )
                }
                HomeState(
                    budget = budgetSummary,
                    categories = categories.map { it.toUi() },
                    recentTransactions = transactionsInfo
                        .take(5)
                        .map { it.toUi() },
                    isLoading = false,
                )
            }
                .collect { newState ->
                    _state.value = newState
                }
        }
    }

    private fun createBudgetSummary(
        budget: Budget,
        expense: Long,
    ): BudgetSummaryUi {
        val remaining = (budget.expenseLimit - expense)
            .coerceAtLeast(0)
        val progress =
            if (budget.expenseLimit == 0L) {
                0f
            } else {
                (expense.toFloat() / budget.expenseLimit)
                    .coerceIn(0f, 1f)
            }

        return BudgetSummaryUi(
            expenseLimit = budget.expenseLimit,
            spent = expense,
            remaining = remaining,
            progress = progress,
        )
    }

    fun saveBudget(expenseLimit: Long) {
        viewModelScope.launch {
            val user = initializeUserUseCase()

            saveCurrentBudgetUseCase(
                userId = user.id,
                expenseLimit = expenseLimit,
            )
        }
    }
}
