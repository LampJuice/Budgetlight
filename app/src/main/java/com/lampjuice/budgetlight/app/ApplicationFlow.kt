package com.lampjuice.budgetlight.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lampjuice.budgetlight.feature.auth.ui.AuthScreen
import com.lampjuice.budgetlight.feature.launcher.ui.LauncherScreen
import com.lampjuice.budgetlight.navigation.BudgetLightNavHost

@Composable
fun ApplicationFlow(
    viewModel: ApplicationFlowViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        ApplicationFlowState.Unauthorized -> {
            AuthScreen(
                onAuthSuccess = {},
            )
        }

        ApplicationFlowState.Launcher -> {
            LauncherScreen(
                onBudgetPlannerClick = viewModel::openBudget,
                onShoppingListClick = {
                    // Пока feature недоступна
                },
                onChangeUserClick = viewModel::logout,
            )
        }

        ApplicationFlowState.Budget -> {
            BudgetLightNavHost()
        }
    }
}
