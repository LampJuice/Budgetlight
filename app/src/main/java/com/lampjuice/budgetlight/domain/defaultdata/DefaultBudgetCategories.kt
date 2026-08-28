package com.lampjuice.budgetlight.domain.defaultdata

import com.lampjuice.budgetlight.feature.budget.domain.model.CategoryIcon

object DefaultBudgetCategories {

    val limits = mapOf(
        CategoryIcon.FOOD to 25_000L,
        CategoryIcon.CAR to 15_000L,
        CategoryIcon.HOME to 10_000L,
        CategoryIcon.ENTERTAINMENT to 10_000L,
        CategoryIcon.SHOPPING to 15_000L,
        CategoryIcon.HEALTH to 10_000L,
        CategoryIcon.TRANSPORT to 5_000L,
        CategoryIcon.OTHER to 10_000L,
    )
}
