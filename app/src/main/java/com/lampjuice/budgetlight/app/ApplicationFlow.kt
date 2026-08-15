package com.lampjuice.budgetlight.app

import androidx.compose.runtime.Composable
import com.lampjuice.budgetlight.feature.launcher.ui.LauncherScreen

@Composable
fun ApplicationFlow() {
    LauncherScreen(
        onBudgetPlannerClick = {
        },
        onShoppingListClick = {
        },
    )
}
