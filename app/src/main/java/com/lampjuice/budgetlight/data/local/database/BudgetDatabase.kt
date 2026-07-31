package com.lampjuice.budgetlight.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lampjuice.budgetlight.data.local.converter.DateConverter
import com.lampjuice.budgetlight.data.local.converter.EnumConverter
import com.lampjuice.budgetlight.data.local.dao.AccountDao
import com.lampjuice.budgetlight.data.local.dao.TransactionDao
import com.lampjuice.budgetlight.data.local.dao.UserDao
import com.lampjuice.budgetlight.data.local.entity.AccountEntity
import com.lampjuice.budgetlight.data.local.entity.TransactionEntity
import com.lampjuice.budgetlight.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        AccountEntity::class,
        TransactionEntity::class,
    ],
    version = 1,
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
}
