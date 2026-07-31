package com.lampjuice.budgetlight.ui.home

import com.lampjuice.budgetlight.ui.home.model.CategoryBudgetUi

internal object HomePreviewData {
    val categories = listOf(
        CategoryBudgetUi(
            id = 1,
            title = "Еда",
            spent = 18_400,
            limit = 25_000,
        ),
        CategoryBudgetUi(
            id = 2,
            title = "Автомобиль",
            spent = 9_800,
            limit = 12_000,
        ),
        CategoryBudgetUi(
            id = 3,
            title = "ЖКХ",
            spent = 8_050,
            limit = 8_000,
        ),
        CategoryBudgetUi(
            id = 4,
            title = "Развлечения",
            spent = 2_500,
            limit = 10_000,
        ),
    )
}
