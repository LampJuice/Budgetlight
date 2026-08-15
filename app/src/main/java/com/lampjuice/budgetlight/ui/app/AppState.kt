package com.lampjuice.budgetlight.ui.app

sealed interface AppState {
    data object Ready : AppState
}
