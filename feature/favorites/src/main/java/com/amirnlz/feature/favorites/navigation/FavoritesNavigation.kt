package com.amirnlz.feature.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.feature.favorites.FavoritesRoute

fun NavController.navigateToFavorites(navOptions: NavOptions? = null) {
    navigate(route = AppRoutes.FavoritesRoute, navOptions)
}


fun NavGraphBuilder.favoritesScreen(
    onMovieClicked: (Long) -> Unit,

    ) {
    composable<AppRoutes.FavoritesRoute> {
        FavoritesRoute(
            onMovieClicked = onMovieClicked
        )
    }
}