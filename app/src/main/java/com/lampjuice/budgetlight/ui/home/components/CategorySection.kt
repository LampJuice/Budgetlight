package com.lampjuice.budgetlight.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lampjuice.budgetlight.R
import com.lampjuice.budgetlight.ui.home.model.CategoryBudgetUi
import com.lampjuice.budgetlight.ui.theme.Dimens

@Composable
fun CategorySection(
    categories: List<CategoryBudgetUi>,
    onCategoryClick: (CategoryBudgetUi) -> Unit,
    onAddCategoryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(
                    R.string.categories_title,
                    categories.size,
                ),
                style = MaterialTheme.typography.titleLarge,
            )

            IconButton(
                onClick = onAddCategoryClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.category_add), // Заменено
                )
            }
        }
        categories.forEach { category ->
            CategoryBudgetItem(
                category = category,
                onClick = { onCategoryClick(category) },
            )
        }
    }
}
