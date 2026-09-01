package com.lampjuice.di

import com.lampjuice.feature.auth.data.security.JwtConfig
import com.lampjuice.feature.auth.data.security.JwtServiceImpl
import com.lampjuice.feature.auth.domain.security.JwtService
import com.lampjuice.feature.auth.data.repository.UserRepositoryImpl
import com.lampjuice.feature.auth.data.security.PasswordHasherImpl
import com.lampjuice.feature.auth.domain.repository.UserRepository
import com.lampjuice.feature.auth.domain.security.PasswordHasher
import com.lampjuice.feature.auth.domain.usecase.LoginUserUseCase
import com.lampjuice.feature.auth.domain.usecase.RegisterUserUseCase
import com.lampjuice.feature.auth.presentation.service.AuthService
import org.koin.dsl.module

fun appModule(
    jwtConfig: JwtConfig
) = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<PasswordHasher> { PasswordHasherImpl() }

    factory {
        RegisterUserUseCase(
            userRepository = get(),
            passwordHasher = get()
        )
    }

    factory {
        LoginUserUseCase(
            userRepository = get(),
            passwordHasher = get()
        )
    }

    single {
        AuthService(
            registerUserUseCase = get(),
            loginUserUseCase = get(),
            jwtService = get()
        )
    }
    single {
        jwtConfig
    }
    single<JwtService> {
        JwtServiceImpl(
            config = get()
        )
    }
}
