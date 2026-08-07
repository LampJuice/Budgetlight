package com.lampjuice.budgetlight.ui.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.lampjuice.budgetlight.domain.model.CategoryIcon

fun CategoryIcon.toImageVector(): ImageVector = when (this) {
    CategoryIcon.FOOD -> Icons.Default.Restaurant
    CategoryIcon.CAR -> Icons.Default.DirectionsCar
    CategoryIcon.HOME -> Icons.Default.Home
    CategoryIcon.SHOPPING -> Icons.Default.ShoppingCart
    CategoryIcon.ENTERTAINMENT -> Icons.Default.Movie
    CategoryIcon.HEALTH -> Icons.Default.Favorite
    CategoryIcon.TRANSPORT -> Icons.Default.DirectionsBus
    CategoryIcon.SALARY -> Icons.Default.AttachMoney
    CategoryIcon.GIFT -> Icons.Default.CardGiftcard
    CategoryIcon.OTHER -> Icons.Default.Category
}
