package com.amirnlz.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector


data class BottomNavItem(
    val route: AppRoutes,
    val label: String,
    val icon: ImageVector,
    val enabled: Boolean = true
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = AppRoutes.HomeRoute,
        label = "Home",
        icon = Icons.Rounded.Home,
        enabled = true
    ),
    BottomNavItem(
        route = AppRoutes.SearchRoute,
        label = "Search",
        icon = Icons.Rounded.Search,
        enabled = true
    ),
    BottomNavItem(
        route = AppRoutes.FavoritesRoute,
        label = "Favorite",
        icon = Icons.Rounded.Favorite,
        enabled = false
    ),
    BottomNavItem(
        route = AppRoutes.SettingRoute,
        label = "Setting",
        icon = Icons.Rounded.Settings,
        enabled = false
    )
)

