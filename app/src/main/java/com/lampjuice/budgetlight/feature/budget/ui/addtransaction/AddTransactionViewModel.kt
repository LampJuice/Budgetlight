package com.lampjuice.budgetlight.feature.budget.ui.addtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.feature.auth.domain.usecase.InitializeUserUseCase
import com.lampjuice.budgetlight.feature.budget.domain.model.Transaction
import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionType
import com.lampjuice.budgetlight.feature.budget.domain.usecase.AddTransactionUseCase
import com.lampjuice.budgetlight.feature.budget.domain.usecase.ObserveCategoriesUseCase
import com.lampjuice.budgetlight.feature.budget.domain.usecase.ObserveCurrentAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val initializeUserUseCase: InitializeUserUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val observeCurrentAccountUseCase: ObserveCurrentAccountUseCase,
    private val observeCategoriesUseCase: ObserveCategoriesUseCase,
) : ViewModel() {

    private val _events = MutableSharedFlow<AddTransactionEvent>()
    val events = _events.asSharedFlow()

    private val _state = MutableStateFlow(AddTransactionState())
    val state = _state.asStateFlow()

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            val user = initializeUserUseCase()

            launch {
                observeCurrentAccountUseCase(user.id).collect { account ->
                    currentAccountId = account?.id
                }
            }
            launch {
                observeCategoriesUseCase().collect { categories ->
                    _state.update {
                        it.copy(categories = categories)
                    }
                }
            }
        }
    }

    private var currentAccountId: Long? = null

    fun onTypeChanged(type: TransactionType) {
        _state.update {
            it.copy(
                type = type,
                selectedCategoryId = null,
            )
        }
    }

    fun onTitleChanged(title: String) {
        _state.update {
            it.copy(title = title)
        }
    }

    fun onAmountChanged(amount: String) {
        _state.update {
            it.copy(amount = amount)
        }
    }

    fun onCategoryChanged(categoryId: Long) {
        _state.update {
            it.copy(selectedCategoryId = categoryId)
        }
    }

    fun onDateChanged(date: LocalDate) {
        _state.update {
            it.copy(date = date)
        }
    }

    fun onSave() {
        val transaction = createTransaction(state.value) ?: return

        viewModelScope.launch {
            _state.update {
                it.copy(isSaving = true)
            }

            addTransactionUseCase(transaction)
            _state.update {
                it.copy(isSaving = false)
            }
            _events.emit(AddTransactionEvent.TransactionSaved)
        }
    }

    private fun createTransaction(state: AddTransactionState): Transaction? {
        val accountId = currentAccountId
        val categoryId = state.selectedCategoryId
        val amount = state.amount.toLongOrNull()

        return if (
            accountId != null &&
            categoryId != null &&
            amount != null

        ) {
            createTransactionOrNull(
                state = state,
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
            )
        } else {
            null
        }
    }
}

private fun createTransactionOrNull(
    state: AddTransactionState,
    accountId: Long,
    categoryId: Long,
    amount: Long,
): Transaction? {
    if (state.title.isBlank() || amount <= 0) {
        return null
    }
    return Transaction(
        id = 0,
        accountId = accountId,
        categoryId = categoryId,
        type = state.type,
        title = state.title.trim(),
        amount = amount,
        date = state.date,
    )
}

sealed interface AddTransactionEvent {
    data object TransactionSaved : AddTransactionEvent
}
