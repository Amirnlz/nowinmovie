package com.amirnlz.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelRoute<T : Any>(
    val name: String,
    val route: T,
    val icon: ImageVector,
    val enabled: Boolean = true,
)


val topLevelRoutes = listOf(
    TopLevelRoute("Home", AppRoutes.HomeRoute, Icons.Rounded.Home),
    TopLevelRoute("Search", AppRoutes.SearchRoute, Icons.Rounded.Search),
    TopLevelRoute("Favorites", AppRoutes.FavoritesRoute, Icons.Rounded.Favorite, enabled = false),
    TopLevelRoute("Settings", AppRoutes.SettingRoute, Icons.Rounded.Settings, enabled = false)
)
