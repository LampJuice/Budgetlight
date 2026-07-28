package com.lampjuice.budgetlight.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lampjuice.budgetlight.domain.model.TransactionType
import com.lampjuice.budgetlight.ui.components.scaffold.BudgetScaffold
import com.lampjuice.budgetlight.ui.home.components.BudgetSummaryCard
import com.lampjuice.budgetlight.ui.home.components.CategorySection
import com.lampjuice.budgetlight.ui.home.components.GreetingSection
import com.lampjuice.budgetlight.ui.home.components.RecentTransactionSection
import com.lampjuice.budgetlight.ui.home.model.CategoryBudgetUi
import com.lampjuice.budgetlight.ui.home.model.TransactionUi
import com.lampjuice.budgetlight.ui.theme.Dimens

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BudgetScaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить операцию",
                )
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + Dimens.FabBottomPadding,
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
        ) {
            item {
                GreetingSection()
            }
            item {
                BudgetSummaryCard()
            }
            item {
                CategorySection(
                    categories = listOf(
                        CategoryBudgetUi(
                            id = 1,
                            icon = "🍔",
                            title = "Еда",
                            spent = 18_400,
                            limit = 25_000,
                        ),
                        CategoryBudgetUi(
                            id = 2,
                            icon = "⛽",
                            title = "Авто",
                            spent = 9_800,
                            limit = 12_000,
                        ),
                        CategoryBudgetUi(
                            id = 3,
                            icon = "🏠",
                            title = "Дом",
                            spent = 8_000,
                            limit = 8_000,
                        ),
                    ),
                )
            }
            item {
                RecentTransactionSection(
                    transactions = listOf(
                        TransactionUi(
                            title = "Зарплата",
                            amount = 75_000,
                            date = "Сегодня",
                            category = "Работа",
                            type = TransactionType.INCOME,
                        ),
                        TransactionUi(
                            title = "Продукты",
                            amount = 850,
                            date = "Сегодня",
                            category = "Еда",
                            type = TransactionType.EXPENSE,
                        ),
                    ),
                )
            }
        }
    }
}
