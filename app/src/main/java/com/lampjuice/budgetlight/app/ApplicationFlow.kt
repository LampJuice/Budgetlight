package com.lampjuice.budgetlight.app

import androidx.compose.runtime.Composable
import com.lampjuice.budgetlight.feature.auth.ui.AuthScreen

@Composable
fun ApplicationFlow() {
    AuthScreen(
        onAuthSuccess = {
        },
    )
//    LauncherScreen(
//        onBudgetPlannerClick = {
//        },
//        onShoppingListClick = {
//        },
//    )
}
