package com.lampjuice.budgetlight.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lampjuice.budgetlight.data.local.converter.DateConverter
import com.lampjuice.budgetlight.data.local.converter.EnumConverter
import com.lampjuice.budgetlight.data.local.dao.AccountDao
import com.lampjuice.budgetlight.data.local.dao.BudgetCategoryDao
import com.lampjuice.budgetlight.data.local.dao.BudgetDao
import com.lampjuice.budgetlight.data.local.dao.CategoryDao
import com.lampjuice.budgetlight.data.local.dao.TransactionDao
import com.lampjuice.budgetlight.data.local.dao.UserDao
import com.lampjuice.budgetlight.data.local.entity.AccountEntity
import com.lampjuice.budgetlight.data.local.entity.BudgetCategoryEntity
import com.lampjuice.budgetlight.data.local.entity.BudgetEntity
import com.lampjuice.budgetlight.data.local.entity.CategoryEntity
import com.lampjuice.budgetlight.data.local.entity.TransactionEntity
import com.lampjuice.budgetlight.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        AccountEntity::class,
        TransactionEntity::class,
        CategoryEntity::class,
        BudgetEntity::class,
        BudgetCategoryEntity::class,
    ],
    version = 4,
    exportSchema = false,
)
@TypeConverters(
    DateConverter::class,
    EnumConverter::class,
)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun accountDao(): AccountDao

    abstract fun transactionDao(): TransactionDao

    abstract fun categoryDao(): CategoryDao

    abstract fun budgetDao(): BudgetDao

    abstract fun budgetCategoryDao(): BudgetCategoryDao
}
