package com.lampjuice.budgetlight.feature.budget.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.feature.auth.domain.usecase.ObserveCurrentUserUseCase
import com.lampjuice.budgetlight.feature.budget.domain.usecase.DeleteTransactionUseCase
import com.lampjuice.budgetlight.feature.budget.domain.usecase.ObserveCurrentAccountUseCase
import com.lampjuice.budgetlight.feature.budget.domain.usecase.ObserveTransactionInfoUseCase
import com.lampjuice.budgetlight.ui.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val observeTransactionInfoUseCase: ObserveTransactionInfoUseCase,
    private val observeCurrentAccountUseCase: ObserveCurrentAccountUseCase,
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionsState())
    val state = _state.asStateFlow()

    init {
        observeTransactions()
    }

    fun onFilterChanged(filter: TransactionFilter) {
        _state.update {
            it.copy(filter = filter)
        }
    }

    fun onDeleteTransaction(transactionId: Long) {
        viewModelScope.launch {
            deleteTransactionUseCase(transactionId)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeTransactions() {
        viewModelScope.launch {
            observeCurrentUserUseCase()
                .filterNotNull()
                .flatMapLatest { user ->
                    observeCurrentAccountUseCase(user.id)
                }
                .flatMapLatest { account ->
                    if (account == null) {
                        flowOf(emptyList())
                    } else {
                        observeTransactionInfoUseCase(account.id)
                    }
                }
                .collect { transactions ->
                    _state.update {
                        it.copy(
                            transactions = transactions.map { transaction ->
                                transaction.toUi()
                            },
                            isLoading = false,
                        )
                    }
                }
        }
    }
}
