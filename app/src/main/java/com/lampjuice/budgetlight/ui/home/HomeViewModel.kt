package com.lampjuice.budgetlight.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.domain.model.CategoryIcon
import com.lampjuice.budgetlight.domain.model.TransactionType
import com.lampjuice.budgetlight.domain.usecase.AddCategoryUseCase
import com.lampjuice.budgetlight.domain.usecase.ArchiveCategoryUseCase
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
    private val addCategoryUseCase: AddCategoryUseCase,
    private val archiveCategoryUseCase: ArchiveCategoryUseCase,
    private val updateBudgetCategoryLimitUseCase: UpdateBudgetCategoryLimitUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    private var userId: Long? = null

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            val user = initializeUserUseCase()
            userId = user.id

            observeHomeDataUseCase(user.id).collect { homeData ->
                _state.value = homeData.toUi(user.name)
            }
        }
    }

    fun saveBudget(expenseLimit: Long) {
        viewModelScope.launch {
            val currentUserId = userId ?: return@launch

            saveCurrentBudgetUseCase(
                userId = currentUserId,
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
    fun addCategory(name: String, icon: CategoryIcon, type: TransactionType) {
        val currentUserId = userId ?: return
        viewModelScope.launch {
            addCategoryUseCase(
                userId = currentUserId,
                name = name,
                icon = icon,
                type = type,
            )
        }
    }

    fun archiveCategory(categoryId: Long) {
        viewModelScope.launch {
            archiveCategoryUseCase(
                categoryId = categoryId,
            )
        }
    }
}
