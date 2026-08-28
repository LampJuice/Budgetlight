package com.lampjuice.budgetlight.feature.budget.ui.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lampjuice.budgetlight.feature.budget.ui.home.components.TransactionItem
import com.lampjuice.budgetlight.ui.components.scaffold.BudgetScaffold
import com.lampjuice.budgetlight.ui.components.segmented.BudgetSegmentedButtons
import com.lampjuice.budgetlight.ui.components.swipe.SwipeToDeleteItem
import com.lampjuice.budgetlight.ui.components.topbar.BudgetTopAppBar
import com.lampjuice.budgetlight.ui.theme.Dimens
import com.lampjuice.budgetlight.ui.util.toTransactionDateString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    onBack: () -> Unit,
    onAddToTransaction: () -> Unit,
    viewModel: TransactionsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val groupedTransactions = state.transactions
        .filter { state.filter.matches(it.type) }
        .sortedByDescending { it.date }
        .groupBy { it.date }

    BudgetScaffold(
        topBar = {
            BudgetTopAppBar(
                title = "Операции",
                onBack = onBack,
            )
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = Dimens.ScreenPadding,
                    end = Dimens.ScreenPadding,
                ),
        ) {
            BudgetSegmentedButtons(
                items = TransactionFilter.entries.toList(),
                selectedItem = state.filter,
                onItemSelected = viewModel::onFilterChanged,
                label = { filter ->
                    when (filter) {
                        TransactionFilter.ALL -> "Все"
                        TransactionFilter.EXPENSE -> "Расход"
                        TransactionFilter.INCOME -> "Доход"
                    }
                },
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = Dimens.LargeSpacing,
                    bottom = paddingValues.calculateBottomPadding() + Dimens.LargeSpacing,
                ),
                verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
            ) {
                if (groupedTransactions.isEmpty()) {
                    item {
                        EmptyTransactionState(onAddTransaction = onAddToTransaction)
                    }
                } else {
                    groupedTransactions.forEach { (date, transactions) ->
                        item(key = "header_$date") {
                            Text(
                                text = date.toTransactionDateString(),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        items(
                            items = transactions,
                            key = { it.id },

                        ) { transaction ->
                            SwipeToDeleteItem(
                                onDelete = { viewModel.onDeleteTransaction(transaction.id) },
                            ) { TransactionItem(transaction = transaction) }
                        }
                    }
                }
            }
        }
    }
}
