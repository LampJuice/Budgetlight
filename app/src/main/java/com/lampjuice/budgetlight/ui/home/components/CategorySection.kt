package com.lampjuice.budgetlight.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lampjuice.budgetlight.R
import com.lampjuice.budgetlight.ui.home.model.CategoryBudgetUi
import com.lampjuice.budgetlight.ui.theme.Dimens

@Composable
fun CategorySection(
    categories: List<CategoryBudgetUi>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing),
    ) {
        Text(
            text = stringResource(R.string.categories_title, categories.size), // Заменено (с аргументом)
            style = MaterialTheme.typography.titleLarge,
        )
        categories.forEach { category ->
            CategoryBudgetItem(category = category)
        }
    }
}
