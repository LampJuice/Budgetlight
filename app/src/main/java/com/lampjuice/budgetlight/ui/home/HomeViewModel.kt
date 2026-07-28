package com.lampjuice.budgetlight.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.domain.usecase.CalculateBalanceUseCase
import com.lampjuice.budgetlight.domain.usecase.InitializeUserUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveCurrentAccountUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveTransactionsUseCase
import com.lampjuice.budgetlight.ui.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
    private val calculateBalanceUseCase: CalculateBalanceUseCase,
    private val observeCurrentAccountUseCase: ObserveCurrentAccountUseCase,
    private val initializeUserUseCase: InitializeUserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        observeTransactions()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeTransactions() {
        viewModelScope.launch {
            val user = initializeUserUseCase()
            observeCurrentAccountUseCase(user.id)
                .flatMapLatest { account ->
                    if (account == null) {
                        flowOf(emptyList())
                    } else {
                        observeTransactionsUseCase(account.id)
                    }
                }
                .collect { transactions ->
                    val balance = calculateBalanceUseCase(transactions)
                    _state.value = HomeState(
                        balance = balance.total,
                        income = balance.income,
                        expense = balance.expense,
                        transactions = transactions.map { it.toUi() },
                        isLoading = false,
                    )
                }
        }
    }
}
