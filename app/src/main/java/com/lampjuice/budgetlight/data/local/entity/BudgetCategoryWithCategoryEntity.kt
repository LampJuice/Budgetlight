package com.lampjuice.budgetlight.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BudgetCategoryWithCategoryEntity(
    @Embedded val budgetCategory: BudgetCategoryEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id",
    )
    val category: CategoryEntity,
)
