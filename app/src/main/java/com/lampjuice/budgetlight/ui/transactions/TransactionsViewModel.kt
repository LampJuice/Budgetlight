package com.lampjuice.budgetlight.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.domain.usecase.InitializeUserUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveCurrentAccountUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveTransactionInfoUseCase
import com.lampjuice.budgetlight.ui.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val observeTransactionInfoUseCase: ObserveTransactionInfoUseCase,
    private val observeCurrentAccountUseCase: ObserveCurrentAccountUseCase,
    private val initializeUserUseCase: InitializeUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionsState())
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
