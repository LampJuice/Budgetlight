package com.lampjuice.budgetlight.di

import android.content.Context
import androidx.room.Room
import com.lampjuice.budgetlight.data.local.database.BudgetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        context: Context,
    ): BudgetDatabase {
        return Room.databaseBuilder(
            context,
            BudgetDatabase::class.java,
            "budget_database",
        ).build()
    }

    @Provides
    fun provideUserDao(database: BudgetDatabase) = database.userDao()

    @Provides
    fun provideAccountDao(database: BudgetDatabase) = database.accountDao()

    @Provides
    fun provideTransactionDao(database: BudgetDatabase) = database.transactionDao()
}
