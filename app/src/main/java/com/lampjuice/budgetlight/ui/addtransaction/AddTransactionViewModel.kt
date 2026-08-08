package com.lampjuice.budgetlight.ui.addtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.domain.usecase.InitializeUserUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveCategoriesUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveCurrentAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val initializeUserUseCase: InitializeUserUseCase,
    private val observeCurrentAccountUseCase: ObserveCurrentAccountUseCase,
    private val observeCategoriesUseCase: ObserveCategoriesUseCase,
) : ViewModel() {
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
}
