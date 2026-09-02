package com.lampjuice.budgetlight.di

import com.lampjuice.budgetlight.feature.auth.data.repository.AuthRepositoryImpl
import com.lampjuice.budgetlight.feature.auth.data.repository.UserRepositoryImpl
import com.lampjuice.budgetlight.feature.auth.data.session.LocalAuthSession
import com.lampjuice.budgetlight.feature.auth.domain.repository.AuthRepository
import com.lampjuice.budgetlight.feature.auth.domain.repository.UserRepository
import com.lampjuice.budgetlight.feature.auth.domain.session.AuthSession
import com.lampjuice.budgetlight.feature.budget.data.repository.AccountRepositoryImpl
import com.lampjuice.budgetlight.feature.budget.data.repository.BudgetCategoryRepositoryImpl
import com.lampjuice.budgetlight.feature.budget.data.repository.BudgetRepositoryImpl
import com.lampjuice.budgetlight.feature.budget.data.repository.CategoryRepositoryImpl
import com.lampjuice.budgetlight.feature.budget.data.repository.TransactionRepositoryImpl
import com.lampjuice.budgetlight.feature.budget.domain.repository.AccountRepository
import com.lampjuice.budgetlight.feature.budget.domain.repository.BudgetCategoryRepository
import com.lampjuice.budgetlight.feature.budget.domain.repository.BudgetRepository
import com.lampjuice.budgetlight.feature.budget.domain.repository.CategoryRepository
import com.lampjuice.budgetlight.feature.budget.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindBudgetRepository(impl: BudgetRepositoryImpl): BudgetRepository

    @Binds
    @Singleton
    abstract fun bindBudgetCategoryRepository(impl: BudgetCategoryRepositoryImpl): BudgetCategoryRepository

    @Binds
    @Singleton
    abstract fun bindAuthSession(impl: LocalAuthSession): AuthSession

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
