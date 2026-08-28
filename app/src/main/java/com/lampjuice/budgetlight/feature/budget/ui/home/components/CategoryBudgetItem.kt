package com.lampjuice.budgetlight.feature.budget.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.lampjuice.budgetlight.feature.budget.domain.model.CategoryIcon
import com.lampjuice.budgetlight.ui.mapper.toImageVector
import com.lampjuice.budgetlight.ui.theme.BudgetLightTheme
import com.lampjuice.budgetlight.ui.theme.Dimens
import com.lampjuice.budgetlight.ui.util.formatMoney

@Composable
fun CategoryBudgetItem(
    category: com.lampjuice.budgetlight.feature.budget.ui.home.model.CategoryBudgetUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(Dimens.CardElevation),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier.padding(Dimens.LargeSpacing),
            verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
                ) {
                    Box(
                        modifier = Modifier
                            .size(Dimens.CategoryIconSize)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = category.icon.toImageVector(),
                            contentDescription = category.title,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                    Text(
                        text = category.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    )
                }
                Text(
                    text = "${category.spent.formatMoney()} / ${category.limit.formatMoney()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (category.limit > 0) {
                LinearProgressIndicator(
                    progress = { category.progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.SmallProgressBarHeight),
                    color =
                    if (category.isOverBudget) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    },

                )
            } else {
                Text(
                    text = "Бюджет не задан",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryBudgetItemPreview() {
    BudgetLightTheme {
        CategoryBudgetItem(
            category = _root_ide_package_.com.lampjuice.budgetlight.feature.budget.ui.home.model.CategoryBudgetUi(
                id = 1,
                icon = CategoryIcon.FOOD,
                title = "Еда",
                spent = 18_400,
                limit = 25_000,
            ),
            onClick = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryBudgetItemOverBudgetPreview() {
    BudgetLightTheme {
        CategoryBudgetItem(
            category = _root_ide_package_.com.lampjuice.budgetlight.feature.budget.ui.home.model.CategoryBudgetUi(
                id = 2,
                icon = CategoryIcon.HOME,
                title = "ЖКХ",
                spent = 10_500,
                limit = 8_000,
            ),
            onClick = {},
        )
    }
}
