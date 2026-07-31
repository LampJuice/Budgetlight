package com.lampjuice.budgetlight.di

import android.content.Context
import androidx.room.Room
import com.lampjuice.budgetlight.data.local.dao.AccountDao
import com.lampjuice.budgetlight.data.local.dao.CategoryDao
import com.lampjuice.budgetlight.data.local.dao.TransactionDao
import com.lampjuice.budgetlight.data.local.dao.UserDao
import com.lampjuice.budgetlight.data.local.database.BudgetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): BudgetDatabase = Room
        .databaseBuilder(
            context,
            BudgetDatabase::class.java,
            "budget_database",
        ).build()

    @Provides
    fun provideUserDao(database: BudgetDatabase): UserDao = database.userDao()

    @Provides
    fun provideAccountDao(database: BudgetDatabase): AccountDao = database.accountDao()

    @Provides
    fun provideTransactionDao(database: BudgetDatabase): TransactionDao = database.transactionDao()

    @Provides
    fun provideCategoryDao(database: BudgetDatabase): CategoryDao = database.categoryDao()
}
