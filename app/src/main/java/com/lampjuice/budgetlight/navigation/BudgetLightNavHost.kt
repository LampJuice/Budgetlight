package com.lampjuice.budgetlight.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lampjuice.budgetlight.feature.budget.ui.addtransaction.AddTransactionScreen
import com.lampjuice.budgetlight.feature.budget.ui.home.HomeScreen
import com.lampjuice.budgetlight.feature.budget.ui.transactions.TransactionsScreen

@Composable
fun BudgetLightNavHost(
    onBackToLauncher: () -> Unit,
) {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    BackHandler(
        enabled = currentRoute == BudgetLightRoutes.HOME,
        onBack = onBackToLauncher,
    )

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
