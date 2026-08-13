package com.lampjuice.budgetlight.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lampjuice.budgetlight.ui.components.scaffold.BudgetScaffold
import com.lampjuice.budgetlight.ui.home.components.BudgetSummaryCard
import com.lampjuice.budgetlight.ui.home.components.CategorySection
import com.lampjuice.budgetlight.ui.home.components.GreetingSection
import com.lampjuice.budgetlight.ui.home.components.RecentTransactionSection
import com.lampjuice.budgetlight.ui.home.components.budgetcard.BudgetEditDialog
import com.lampjuice.budgetlight.ui.home.components.categorysection.BudgetCategoryEditDialog
import com.lampjuice.budgetlight.ui.home.model.CategoryBudgetUi
import com.lampjuice.budgetlight.ui.theme.Dimens

@Composable
fun HomeScreen(
    onAddNewTransaction: () -> Unit,
    onShowAllTransactions: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showBudgetDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedCategory by rememberSaveable {
        mutableStateOf<CategoryBudgetUi?>(null)
    }

    BudgetScaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNewTransaction,
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
                start = Dimens.ScreenPadding,
                end = Dimens.ScreenPadding,
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
        ) {
            item {
                GreetingSection(
                    userName = state.userName,
                    month = state.month,
                )
            }
            item {
                state.budget?.let { budget ->
                    BudgetSummaryCard(
                        budget = budget,
                        onEditCLick = {
                            showBudgetDialog = true
                        },
                    )
                }
            }
            item { Spacer(Modifier.height(Dimens.LargeSpacing)) }
            item {
                CategorySection(
                    categories = state.categories,
                    onCategoryClick = { category ->
                        selectedCategory = category
                    },

                )
            }
            item {
                RecentTransactionSection(
                    transactions = state.recentTransactions,
                    onShowAllClick = onShowAllTransactions,
                )
            }
        }
    }

    if (showBudgetDialog) {
        BudgetEditDialog(
            currentLimit = state.budget?.expenseLimit ?: 0L,
            onDismiss = { showBudgetDialog = false },
            onSave = { limit ->
                viewModel.saveBudget(limit)
                showBudgetDialog = false
            },

        )
    }

    selectedCategory?.let { category ->
        BudgetCategoryEditDialog(
            category = category,
            onDismiss = {
                selectedCategory = null
            },
            onSave = { limit ->
                viewModel.updateCategoryLimit(category.id, limit)
                selectedCategory = null
            },

        )
    }
}
