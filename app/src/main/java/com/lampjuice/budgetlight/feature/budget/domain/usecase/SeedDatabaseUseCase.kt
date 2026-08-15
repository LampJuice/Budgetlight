package com.lampjuice.budgetlight.feature.budget.domain.usecase

import com.lampjuice.budgetlight.feature.budget.domain.model.Account
import com.lampjuice.budgetlight.feature.budget.domain.model.CategoryIcon
import com.lampjuice.budgetlight.feature.budget.domain.model.Transaction
import com.lampjuice.budgetlight.feature.budget.domain.model.TransactionType
import com.lampjuice.budgetlight.feature.budget.domain.repository.AccountRepository
import com.lampjuice.budgetlight.feature.budget.domain.repository.CategoryRepository
import com.lampjuice.budgetlight.feature.budget.domain.repository.TransactionRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class SeedDatabaseUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
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
        val categories = categoryRepository.observeCategories().first()

        val salaryCategoryId = categories.first {
            it.icon == CategoryIcon.SALARY
        }.id

        val carCategoryId = categories.first {
            it.icon == CategoryIcon.CAR
        }.id

        val foodCategoryId = categories.first {
            it.icon == CategoryIcon.FOOD
        }.id

        transactionRepository.addTransaction(
            Transaction(
                id = 0,
                accountId = accountId,
                title = "Зарплата",
                amount = 75000,
                categoryId = salaryCategoryId,
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
                categoryId = carCategoryId,
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
                categoryId = foodCategoryId,
                date = LocalDate.now(),
                type = TransactionType.EXPENSE,
            ),
        )
    }
}
