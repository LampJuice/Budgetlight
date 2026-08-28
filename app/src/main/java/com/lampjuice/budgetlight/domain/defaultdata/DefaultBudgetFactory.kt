package com.lampjuice.budgetlight.domain.defaultdata

import com.lampjuice.budgetlight.feature.budget.domain.model.Budget
import com.lampjuice.budgetlight.feature.budget.domain.model.BudgetCategory
import com.lampjuice.budgetlight.feature.budget.domain.model.Category
import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionType
import java.time.LocalDate

object DefaultBudgetFactory {
    fun createBudget(
        userId: Long,
    ): Budget {
        val now = LocalDate.now()
        return Budget(
            id = 0,
            userId = userId,
            year = now.year,
            month = now.monthValue,
            plannedIncome = 0,
            expenseLimit = 0,
        )
    }

    fun createBudgetCategories(
        budgetId: Long,
        categories: List<Category>,
    ): List<BudgetCategory> = categories
        .filter { it.type == TransactionType.EXPENSE }
        .map { category ->

            BudgetCategory(
                id = 0,
                budgetId = budgetId,
                categoryId = category.id,
                plannedAmount = DefaultBudgetCategories.limits[category.icon] ?: 0L,
            )
        }
}
