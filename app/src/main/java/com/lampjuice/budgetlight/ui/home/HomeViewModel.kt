package com.lampjuice.budgetlight.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.domain.model.Transaction
import com.lampjuice.budgetlight.domain.model.TransactionType
import com.lampjuice.budgetlight.domain.usecase.ObserveTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
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
                    _state.value = HomeState(
                        transactions = transactions,
                        balance = calculateBalance(transactions),
                        isLoading = false,
                    )
                }
        }
    }
    private fun calculateBalance(transactions: List<Transaction>): Long = transactions.sumOf {
        when (it.type) {
            TransactionType.INCOME -> it.amount
            TransactionType.EXPENSE -> -it.amount
        }
    }
}
