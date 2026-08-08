package com.lampjuice.budgetlight.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lampjuice.budgetlight.ui.addtransaction.AddTransactionScreen
import com.lampjuice.budgetlight.ui.home.HomeScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(
            route = AppRoutes.HOME,
        ) {
            HomeScreen(
                onAddNewTransaction = {
                    navController.navigate(AppRoutes.ADD_TRANSACTION)
                },
            )
        }
        composable(
            route = AppRoutes.ADD_TRANSACTION,
        ) {
            AddTransactionScreen(
                onBack = navController::popBackStack,
            )
        }
    }
}
