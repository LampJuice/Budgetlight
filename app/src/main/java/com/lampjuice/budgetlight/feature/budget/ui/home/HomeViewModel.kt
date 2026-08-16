package com.lampjuice.budgetlight.feature.budget.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.feature.auth.domain.usecase.ObserveCurrentUserUseCase
import com.lampjuice.budgetlight.feature.budget.domain.model.CategoryIcon
import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionType
import com.lampjuice.budgetlight.feature.budget.domain.usecase.AddCategoryUseCase
import com.lampjuice.budgetlight.feature.budget.domain.usecase.ArchiveCategoryUseCase
import com.lampjuice.budgetlight.feature.budget.domain.usecase.ObserveHomeDataUseCase
import com.lampjuice.budgetlight.feature.budget.domain.usecase.SaveCurrentBudgetUseCase
import com.lampjuice.budgetlight.feature.budget.domain.usecase.UpdateBudgetCategoryLimitUseCase
import com.lampjuice.budgetlight.feature.budget.ui.home.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeHomeDataUseCase: ObserveHomeDataUseCase,
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase,
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun initialize() {
        viewModelScope.launch {
            observeCurrentUserUseCase()
                .filterNotNull()
                .flatMapLatest { user ->
                    userId = user.id

                    observeHomeDataUseCase(user.id)
                        .map { homeData ->
                            user to homeData
                        }
                }
                .collect { (user, homeData) ->
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
