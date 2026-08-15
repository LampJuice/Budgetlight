package com.lampjuice.budgetlight.di

import com.lampjuice.budgetlight.feature.auth.data.security.PasswordHasherImpl
import com.lampjuice.budgetlight.feature.auth.domain.security.PasswordHasher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindsPasswordHasher(impl: PasswordHasherImpl): PasswordHasher
}
