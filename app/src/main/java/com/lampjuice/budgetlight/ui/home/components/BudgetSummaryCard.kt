package com.lampjuice.budgetlight.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lampjuice.budgetlight.R
import com.lampjuice.budgetlight.ui.home.model.BudgetSummaryUi
import com.lampjuice.budgetlight.ui.theme.Dimens
import com.lampjuice.budgetlight.ui.theme.GreenIncome
import com.lampjuice.budgetlight.ui.util.formatMoney

@Composable
fun BudgetSummaryCard(
    budget: BudgetSummaryUi,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation),

    ) {
        Column(
            modifier = Modifier.padding(Dimens.ExtraLargeSpacing),
            verticalArrangement = Arrangement.spacedBy(Dimens.CardCorner),
        ) {
            Text(
                text = stringResource(R.string.budget_of_month),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = budget.expenseLimit.formatMoney(),
                style = MaterialTheme.typography.headlineLarge,
            )

            LinearProgressIndicator(
                progress = { budget.progress },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.LargeSpacing),
                color = if (budget.progress >= 1f) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                },
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
            ) {
                BudgetValueRow(
                    title = stringResource(R.string.spent),
                    value = budget.spent.formatMoney(),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                BudgetValueRow(
                    title = stringResource(R.string.remaining),
                    value = budget.remaining.formatMoney(),
                    color = GreenIncome,
                )
            }
        }
    }
}
