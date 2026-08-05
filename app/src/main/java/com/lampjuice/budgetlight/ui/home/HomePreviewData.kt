package com.lampjuice.budgetlight.ui.home

import com.lampjuice.budgetlight.ui.home.model.CategoryBudgetUi

internal object HomePreviewData {
    val categories = listOf(
        CategoryBudgetUi(
            id = 1,
            icon = "FOOD",
            title = "Еда",
            spent = 18_400,
            limit = 25_000,
        ),
        CategoryBudgetUi(
            id = 2,
            icon = "CAR",
            title = "Автомобиль",
            spent = 9_800,
            limit = 12_000,
        ),
        CategoryBudgetUi(
            id = 3,
            icon = "HOME",
            title = "ЖКХ",
            spent = 8_050,
            limit = 8_000,
        ),
        CategoryBudgetUi(
            id = 4,
            icon = "ENTERTAINMENT",
            title = "Развлечения",
            spent = 2_500,
            limit = 10_000,
        ),
    )
}
