package com.lampjuice.budgetlight.di

import com.lampjuice.budgetlight.data.repository.AccountRepositoryImpl
import com.lampjuice.budgetlight.data.repository.TransactionRepositoryImpl
import com.lampjuice.budgetlight.data.repository.UserRepositoryImpl
import com.lampjuice.budgetlight.domain.repository.AccountRepository
import com.lampjuice.budgetlight.domain.repository.TransactionRepository
import com.lampjuice.budgetlight.domain.repository.UserRepository
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
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(
        impl: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        impl: TransactionRepositoryImpl
    ): TransactionRepository
}
