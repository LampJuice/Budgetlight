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
import com.lampjuice.budgetlight.ui.components.BalanceCard
import com.lampjuice.budgetlight.ui.components.SectionHeader
import com.lampjuice.budgetlight.ui.theme.Dimens

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
        BalanceCard(
            balance = state.balance,
            income = state.income,
            expense = state.expense,
        )
        SectionHeader(
            text = "Последние транзакции",
        )
        state.transactions.forEach {
            TransactionItem(it)
        }
    }
}
