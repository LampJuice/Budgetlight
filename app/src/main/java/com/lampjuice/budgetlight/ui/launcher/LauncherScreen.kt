package com.lampjuice.budgetlight.ui.launcher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.lampjuice.budgetlight.R
import com.lampjuice.budgetlight.ui.theme.Dimens

private data class LauncherFeature(
    val title: String,
    val description: String,
    val enabled: Boolean,
    val icon: ImageVector,
    val onClick: () -> Unit,
)

@Composable
fun LauncherScreen(
    onBudgetPlannerClick: () -> Unit,
    onShoppingListClick: () -> Unit,
) {
    val features = listOf(
        LauncherFeature(
            title = stringResource(R.string.budget_planner_title),
            description = stringResource(R.string.budget_planner_desc),
            enabled = true,
            icon = Icons.Default.Receipt,
            onClick = onBudgetPlannerClick,
        ),
        LauncherFeature(
            title = stringResource(R.string.shopping_list_title),
            description = stringResource(R.string.shopping_list_desc),
            enabled = true,
            icon = Icons.Default.AccountBalanceWallet,
            onClick = onShoppingListClick,
        ),
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = Dimens.LargeSpacing,
            vertical = Dimens.XXLSpacing,
        ),
        verticalArrangement = Arrangement.spacedBy(Dimens.LargeSpacing),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineLarge,

                )

                Spacer(modifier = Modifier.size(Dimens.SmallSpacing))

                Text(
                    text = stringResource(R.string.app_choice),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,

                )
            }
        }
        items(features) { feature ->
            LauncherFeatureCard(feature = feature)
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.ItemSpacing),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.have_an_account),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(modifier = Modifier.size(Dimens.ExtraSmallSpacing))

                Text(
                    text = stringResource(R.string.sign_in),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Composable
private fun LauncherFeatureCard(
    feature: LauncherFeature,
) {
    Card(
        onClick = feature.onClick,
        enabled = feature.enabled,
        modifier = Modifier
            .fillMaxSize()
            .alpha(if (feature.enabled) 1f else 0.5f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.LargeSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing),
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = null,
                modifier = Modifier.size(Dimens.ExtraLargeSpacing),
                tint = MaterialTheme.colorScheme.primary,
            )

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = feature.title,
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(modifier = Modifier.size(Dimens.CardElevation))
                Text(
                    text = feature.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,

                )
            }
        }
    }
}
