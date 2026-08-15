package com.lampjuice.budgetlight.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lampjuice.budgetlight.ui.addtransaction.AddTransactionScreen
import com.lampjuice.budgetlight.ui.home.HomeScreen
import com.lampjuice.budgetlight.ui.transactions.TransactionsScreen

@Composable
fun BudgetLightNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = BudgetLightRoutes.HOME) {
        composable(
            route = BudgetLightRoutes.HOME,
        ) {
            HomeScreen(
                onAddNewTransaction = {
                    navController.navigate(BudgetLightRoutes.ADD_TRANSACTION)
                },
                onShowAllTransactions = {
                    navController.navigate(BudgetLightRoutes.TRANSACTIONS)
                },
            )
        }
        composable(
            route = BudgetLightRoutes.ADD_TRANSACTION,
        ) {
            AddTransactionScreen(
                onBack = navController::popBackStack,
            )
        }
        composable(
            route = BudgetLightRoutes.TRANSACTIONS,
        ) {
            TransactionsScreen(
                onBack = navController::popBackStack,
                onAddToTransaction = {
                    navController.navigate(BudgetLightRoutes.ADD_TRANSACTION)
                },
            )
        }
    }
}
