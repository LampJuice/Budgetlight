package com.lampjuice.budgetlight.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.feature.auth.domain.session.AuthSession
import com.lampjuice.budgetlight.feature.auth.domain.usecase.ObserveCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ApplicationFlowViewModel @Inject constructor(
    observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val authSession: AuthSession,
) : ViewModel() {

    private val _state = MutableStateFlow<ApplicationFlowState>(
        ApplicationFlowState.Unauthorized,
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeCurrentUserUseCase().collect { user ->
                _state.value = if (user == null) {
                    ApplicationFlowState.Unauthorized
                } else {
                    ApplicationFlowState.Launcher
                }
            }
        }
    }

    fun openBudget() {
        _state.value = ApplicationFlowState.Budget
    }

    fun openLauncher() {
        _state.value = ApplicationFlowState.Launcher
    }

    fun logout() {
        viewModelScope.launch {
            authSession.clear()
        }
    }
}
