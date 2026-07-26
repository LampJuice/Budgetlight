package com.lampjuice.budgetlight.ui.app

sealed interface AppState {
    data object Loading : AppState

    data object Ready : AppState
}
