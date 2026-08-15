package com.lampjuice.budgetlight.feature.budget.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lampjuice.budgetlight.feature.budget.domain.model.CategoryIcon
import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionType

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: TransactionType,
    val icon: CategoryIcon,
    val isArchived: Boolean,
)
