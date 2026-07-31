package com.lampjuice.budgetlight.data.local.converter

import androidx.room.TypeConverter
import com.lampjuice.budgetlight.domain.model.CategoryIcon
import com.lampjuice.budgetlight.domain.model.TransactionType

class EnumConverter {
    @TypeConverter
    fun fromTransactionType(type: TransactionType): String = type.name

    @TypeConverter
    fun toTransactionType(value: String): TransactionType = TransactionType.valueOf(value)

    @TypeConverter
    fun fromCategoryIcon(icon: CategoryIcon): String = icon.name

    @TypeConverter
    fun toCategoryIcon(value: String): CategoryIcon = CategoryIcon.valueOf(value)
}
