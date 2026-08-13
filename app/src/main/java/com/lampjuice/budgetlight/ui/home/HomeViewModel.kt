package com.lampjuice.budgetlight.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.domain.usecase.InitializeUserUseCase
import com.lampjuice.budgetlight.domain.usecase.ObserveHomeDataUseCase
import com.lampjuice.budgetlight.domain.usecase.SaveCurrentBudgetUseCase
import com.lampjuice.budgetlight.domain.usecase.UpdateBudgetCategoryLimitUseCase
import com.lampjuice.budgetlight.ui.home.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeHomeDataUseCase: ObserveHomeDataUseCase,
    private val initializeUserUseCase: InitializeUserUseCase,
    private val saveCurrentBudgetUseCase: SaveCurrentBudgetUseCase,
    private val updateBudgetCategoryLimitUseCase: UpdateBudgetCategoryLimitUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        observeHomeData()
    }

    private fun observeHomeData() {
        viewModelScope.launch {
            val user = initializeUserUseCase()

            observeHomeDataUseCase(user.id).collect { homeData ->
                _state.value = homeData.toUi()
            }
        }
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

    fun updateCategoryLimit(categoryId: Long, limit: Long) {
        val budgetId = state.value.budget?.id ?: return
        viewModelScope.launch {
            updateBudgetCategoryLimitUseCase(
                budgetId = budgetId,
                categoryId = categoryId,
                plannedAmount = limit,
            )
        }
    }
}
