package com.lampjuice.budgetlight.app

sealed interface ApplicationFlowState {
    data object Unauthorized : ApplicationFlowState
    data object Budget : ApplicationFlowState
    data object Launcher : ApplicationFlowState
}
