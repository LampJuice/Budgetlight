package com.lampjuice.budgetlight.ui.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.domain.usecase.InitializeUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AppViewModel
@Inject
constructor(
    private val initializeUserUseCase: InitializeUserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<AppState>(AppState.Loading)

    val state = _state.asStateFlow()

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            initializeUserUseCase()
            _state.value = AppState.Ready
        }
    }
}
