package com.lampjuice.budgetlight.ui.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampjuice.budgetlight.domain.usecase.InitializeUserUseCase
import com.lampjuice.budgetlight.domain.usecase.SeedApplicationDataUseCase
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
    private val seedApplicationDataUseCase: SeedApplicationDataUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<AppState>(AppState.Loading)

    val state = _state.asStateFlow()

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            val user = initializeUserUseCase()

            seedApplicationDataUseCase(
                userId = user.id,
            )

            _state.value = AppState.Ready
        }
    }
}
