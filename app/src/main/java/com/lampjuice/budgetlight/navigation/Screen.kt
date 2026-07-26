package com.lampjuice.budgetlight.navigation

sealed class Screen(
    val route: String,
) {
    data object Home : Screen("home")
}
