package com.lampjuice.budgetlight.domain.defaultdata

import com.lampjuice.budgetlight.feature.budget.domain.model.Category
import com.lampjuice.budgetlight.feature.budget.domain.model.CategoryIcon
import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionType

internal object DefaultCategories {
    val list = listOf(
        Category(
            id = 0,
            name = "Еда",
            type = TransactionType.EXPENSE,
            icon = CategoryIcon.FOOD,
        ),
        Category(
            id = 0,
            name = "Автомобиль",
            type = TransactionType.EXPENSE,
            icon = CategoryIcon.CAR,
        ),
        Category(
            id = 0,
            name = "Дом",
            type = TransactionType.EXPENSE,
            icon = CategoryIcon.HOME,
        ),
        Category(
            id = 0,
            name = "Развлечения",
            type = TransactionType.EXPENSE,
            icon = CategoryIcon.ENTERTAINMENT,
        ),
        Category(
            id = 0,
            name = "Зарплата",
            type = TransactionType.INCOME,
            icon = CategoryIcon.SALARY,
        ),
    )
}
