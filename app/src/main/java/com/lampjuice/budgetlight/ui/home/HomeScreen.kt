package com.lampjuice.budgetlight.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lampjuice.budgetlight.ui.components.BudgetCard
import com.lampjuice.budgetlight.ui.components.MoneyText
import com.lampjuice.budgetlight.ui.components.SectionHeader
import com.lampjuice.budgetlight.ui.theme.Dimens
import com.lampjuice.budgetlight.ui.theme.GreenIncome
import com.lampjuice.budgetlight.ui.theme.RedExpense

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
                amount = state.balance.toDouble(),
            )
        }

        BudgetCard {
            SectionHeader(
                text = "Статистика",
            )
            MoneyText(
                amount = state.income.toDouble(),
                color = GreenIncome,

            )
            MoneyText(
                amount = state.expense.toDouble(),
                color = RedExpense,
            )
        }
        SectionHeader(
            text = "Последние транзакции",
        )
        state.transactions.forEach { transaction ->
            TransactionItem(
                transaction = transaction,
            )
        }
    }
}
