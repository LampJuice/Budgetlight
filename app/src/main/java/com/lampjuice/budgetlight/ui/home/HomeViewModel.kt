package com.lampjuice.budgetlight.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.domain.model.Balance
import com.lampjuice.budgetlight.domain.usecase.CalculateBalanceUseCase
import com.lampjuice.budgetlight.domain.usecase.InitializeUserUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveCategoriesUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveCurrentAccountUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveTransactionsUseCase
import com.lampjuice.budgetlight.ui.home.model.BudgetSummaryUi
import com.lampjuice.budgetlight.ui.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
    private val calculateBalanceUseCase: CalculateBalanceUseCase,
    private val observeCurrentAccountUseCase: ObserveCurrentAccountUseCase,
    private val initializeUserUseCase: InitializeUserUseCase,
    private val observeCategoriesUseCase: ObserveCategoriesUseCase,
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
            val transactionsFlow = observeCurrentAccountUseCase(user.id)
                .flatMapLatest { account ->
                    if (account == null) {
                        flowOf(emptyList())
                    } else {
                        observeTransactionsUseCase(account.id)
                    }
                }
            combine(
                transactionsFlow,
                observeCategoriesUseCase(),

            ) { transactions, categories ->
                val balance = calculateBalanceUseCase(transactions)
                HomeState(
                    budget = createBudgetSummary(balance),
                    categories = categories.map { it.toUi() },
                    recentTransactions = transactions
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
        balance: Balance,
    ): BudgetSummaryUi {
        val remaining = (balance.total - balance.expense)
            .coerceAtLeast(0)

        return BudgetSummaryUi(
            budget = balance.total,
            spent = balance.expense,
            remaining = remaining,
            progress = 0.32f,
        )
    }
}
