package com.lampjuice.budgetlight.ui.components.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BudgetScaffold(
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = floatingActionButton,
        content = content,
    )
}
