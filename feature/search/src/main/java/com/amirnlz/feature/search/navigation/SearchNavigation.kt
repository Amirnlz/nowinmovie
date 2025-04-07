package com.amirnlz.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.feature.search.SearchRoute

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(route = AppRoutes.SearchRoute, navOptions)
}


fun NavGraphBuilder.searchScreen(
    onMovieClicked: (Long) -> Unit,

    ) {
    composable<AppRoutes.SearchRoute> {
        SearchRoute(
            onMovieClicked = onMovieClicked
        )
    }
}