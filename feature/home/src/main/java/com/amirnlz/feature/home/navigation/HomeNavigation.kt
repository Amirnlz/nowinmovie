package com.amirnlz.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.feature.home.HomeRoute


fun NavController.navigateToHome(navOptions: NavOptions) =
    navigate(route = AppRoutes.HomeRoute, navOptions)


fun NavGraphBuilder.homeScreen(
    onMovieClicked: (Long) -> Unit,
    onSeriesClicked: (Long) -> Unit,
) {
    composable<AppRoutes.HomeRoute> {
        HomeRoute(
            onMovieClicked = onMovieClicked,
            onSeriesClicked = onSeriesClicked,
        )
    }
}