package com.lampjuice.budgetlight.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lampjuice.budgetlight.navigation.AppNavHost
import com.lampjuice.budgetlight.ui.app.AppState
import com.lampjuice.budgetlight.ui.app.AppViewModel
import com.lampjuice.budgetlight.ui.loadingscreen.LoadingScreen

@Composable
fun BudgetApp(
    viewModel: AppViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        AppState.Loading -> LoadingScreen()
        AppState.Ready -> AppNavHost()
    }
}
