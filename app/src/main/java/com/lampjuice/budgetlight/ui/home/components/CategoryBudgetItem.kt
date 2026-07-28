package com.lampjuice.budgetlight.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lampjuice.budgetlight.ui.home.model.CategoryBudgetUi
import com.lampjuice.budgetlight.ui.theme.BudgetLightTheme
import com.lampjuice.budgetlight.ui.theme.Dimens
import com.lampjuice.budgetlight.ui.util.formatMoney

@Composable
fun CategoryBudgetItem(
    category: CategoryBudgetUi,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(Dimens.CardElevation),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier.padding(Dimens.CardCorner),
            verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
            ) {
                Text(
                    text = category.icon,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Text(
                text = "${category.spent.formatMoney()} / ${category.limit.formatMoney()}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            LinearProgressIndicator(
                progress = { category.progress },
                modifier = Modifier.fillMaxWidth(),
                color =
                if (category.isOverBudget) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryBudgetItemPreview() {
    BudgetLightTheme {
        CategoryBudgetItem(
            category = CategoryBudgetUi(
                id = 1,
                icon = "🍔",
                title = "Еда",
                spent = 18_400,
                limit = 25_000,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryBudgetItemOverBudgetPreview() {
    BudgetLightTheme {
        CategoryBudgetItem(
            category = CategoryBudgetUi(
                id = 2,
                icon = "🏠",
                title = "ЖКХ",
                spent = 10_500,
                limit = 8_000,
            ),
        )
    }
}
