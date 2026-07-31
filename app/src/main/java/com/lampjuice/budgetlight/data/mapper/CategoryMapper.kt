package com.lampjuice.budgetlight.data.mapper

import com.lampjuice.budgetlight.data.local.entity.CategoryEntity
import com.lampjuice.budgetlight.domain.model.Category

fun CategoryEntity.toDomain(): Category = Category(
    id = id,
    name = name,
    type = type,
    icon = icon,
    isArchived = isArchived,
)
fun Category.toEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    type = type,
    icon = icon,
    isArchived = isArchived,
)
