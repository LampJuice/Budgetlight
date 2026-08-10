package com.lampjuice.budgetlight.ui.components.segmented

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> BudgetSegmentedButtons(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    label: @Composable (T) -> String,
    modifier: Modifier = Modifier,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier.fillMaxWidth(),
    ) {
        items.forEachIndexed { index, item ->
            SegmentedButton(
                selected = item == selectedItem,
                onClick = {
                    onItemSelected(item)
                },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = items.size,
                ),
            ) {
                Text(text = label(item))
            }
        }
    }
}
