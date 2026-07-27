package com.lampjuice.budgetlight.domain.usecase

import com.lampjuice.budgetlight.domain.model.Account
import com.lampjuice.budgetlight.domain.model.Transaction
import com.lampjuice.budgetlight.domain.model.TransactionType
import com.lampjuice.budgetlight.domain.repository.AccountRepository
import com.lampjuice.budgetlight.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class SeedDatabaseUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) {

    suspend operator fun invoke(userId: Long) {
        val accounts = accountRepository
            .observeAccounts(userId)
            .first()

        if (accounts.isNotEmpty()) return

        val accountId = accountRepository.addAccount(
            Account(
                id = 0,
                userId = userId,
                name = "Основной счет",
            ),
        )

        transactionRepository.addTransaction(
            Transaction(
                id = 0,
                accountId = accountId,
                title = "Зарплата",
                amount = 75000,
                category = "Работа",
                date = LocalDate.now(),
                type = TransactionType.INCOME,
            ),
        )

        transactionRepository.addTransaction(
            Transaction(
                id = 0,
                accountId = accountId,
                title = "Топливо",
                amount = 3000,
                category = "Авто",
                date = LocalDate.now().minusDays(1),
                type = TransactionType.EXPENSE,
            ),
        )

        transactionRepository.addTransaction(
            Transaction(
                id = 0,
                accountId = accountId,
                title = "Продукты",
                amount = 850,
                category = "Еда",
                date = LocalDate.now(),
                type = TransactionType.EXPENSE,
            ),
        )
    }
}
