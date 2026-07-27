package com.lampjuice.budgetlight.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.domain.usecase.CalculateBalanceUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveTransactionsUseCase
import com.lampjuice.budgetlight.ui.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
    private val calculateBalanceUseCase: CalculateBalanceUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val currentAccountId = 1L

    init {
        observeTransactions()
    }

    private fun observeTransactions() {
        viewModelScope.launch {
            observeTransactionsUseCase(accountId = currentAccountId)
                .collect { transactions ->

                    val balance = calculateBalanceUseCase(transactions)
                    _state.value = HomeState(
                        balance = balance.total,
                        income = balance.income,
                        expense = balance.expense,

                        transactions = transactions.map {
                            it.toUi()
                        },
                        isLoading = false,

                    )
                }
        }
    }
}
