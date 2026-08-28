package com.lampjuice.budgetlight.di

import android.content.Context
import androidx.room.Room
import com.lampjuice.budgetlight.database.BudgetDatabase
import com.lampjuice.budgetlight.database.MIGRATION_3_4
import com.lampjuice.budgetlight.feature.auth.data.local.dao.UserDao
import com.lampjuice.budgetlight.feature.budget.data.local.dao.AccountDao
import com.lampjuice.budgetlight.feature.budget.data.local.dao.BudgetCategoryDao
import com.lampjuice.budgetlight.feature.budget.data.local.dao.BudgetDao
import com.lampjuice.budgetlight.feature.budget.data.local.dao.CategoryDao
import com.lampjuice.budgetlight.feature.budget.data.local.dao.TransactionDao
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
        )
        .addMigrations(
            MIGRATION_3_4,
        )
        .build()

    @Provides
    fun provideUserDao(database: BudgetDatabase): UserDao = database.userDao()

    @Provides
    fun provideAccountDao(database: BudgetDatabase): AccountDao = database.accountDao()

    @Provides
    fun provideTransactionDao(database: BudgetDatabase): TransactionDao = database.transactionDao()

    @Provides
    fun provideCategoryDao(database: BudgetDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideBudgetDao(database: BudgetDatabase): BudgetDao = database.budgetDao()

    @Provides
    fun provideBudgetCategoryDao(database: BudgetDatabase): BudgetCategoryDao = database.budgetCategoryDao()
}
