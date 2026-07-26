package com.lampjuice.budgetlight.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lampjuice.budgetlight.ui.components.BudgetCard
import com.lampjuice.budgetlight.ui.components.MoneyText
import com.lampjuice.budgetlight.ui.components.SectionHeader
import com.lampjuice.budgetlight.ui.theme.Dimens
import com.lampjuice.budgetlight.ui.theme.GreenIncome
import com.lampjuice.budgetlight.ui.theme.RedExpense

@Composable
fun HomeScreen() {
    val transactions = listOf(
        TransactionUi(
            title = "Перевод",
            amount = 750.5,
            date = "05.06.2023",
        ),
        TransactionUi(
            title = "Зарплата",
            amount = 75000.0,
            date = "12.02.2023",
        ),
        TransactionUi(
            title = "Топливо",
            amount = -4444.5,
            date = "12.02.2023",
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Dimens.ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
    ) {
        SectionHeader(
            text = "Добро пожаловать! \uD83D\uDC4B",
        )
        BudgetCard {
            SectionHeader(
                text = "Баланс",
            )
            MoneyText(
                amount = 42580.0,
            )
        }

        BudgetCard {
            SectionHeader(
                text = "Статистика",
            )
            MoneyText(
                amount = 72580.0,
                color = GreenIncome,

            )
            MoneyText(
                amount = -12580.0,
                color = RedExpense,
            )
        }
        SectionHeader(
            text = "Последние транзакции",
        )
        transactions.forEach {
            TransactionItem(transaction = it)
        }
    }
}
