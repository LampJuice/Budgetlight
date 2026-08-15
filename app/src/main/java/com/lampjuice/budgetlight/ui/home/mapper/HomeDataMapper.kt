package com.lampjuice.budgetlight.ui.home.mapper

import com.lampjuice.budgetlight.domain.model.HomeData
import com.lampjuice.budgetlight.ui.home.HomeState
import com.lampjuice.budgetlight.ui.home.model.BudgetSummaryUi
import com.lampjuice.budgetlight.ui.mapper.toUi
import com.lampjuice.budgetlight.ui.util.toMonthYearString
import java.time.LocalDate

fun HomeData.toUi(userName: String): HomeState {
    val budgetSummary = budget?.let {
        val monthlyExpenses = budgetCategories.sumOf { category ->
            category.spentAmount
        }

        BudgetSummaryUi(
            id = budget.id,
            expenseLimit = budget.expenseLimit,
            spent = monthlyExpenses,
            remaining = (budget.expenseLimit - monthlyExpenses)
                .coerceAtLeast(0),
            progress = if (budget.expenseLimit == 0L) {
                0F
            } else {
                (monthlyExpenses.toFloat() / budget.expenseLimit)
                    .coerceIn(0f, 1f)
            },
        )
    }
    return HomeState(
        userName = userName,
        month = budget?.let {
            LocalDate.of(it.year, it.month, 1)
                .toMonthYearString()
        } ?: "",
        budget = budgetSummary,
        categories = budgetCategories.map { it.toUi() },
        recentTransactions = transactionsInfo
            .take(5)
            .map { it.toUi() },
    )
}
