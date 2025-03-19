package com.amirnlz.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.feature.home.HomeScreen


fun NavController.navigateToHome(navOptions: NavOptions) =
    navigate(route = AppRoutes.HomeRoute, navOptions)


fun NavGraphBuilder.homeSection(
    onMovieClicked: (Long) -> Unit,
    onSeriesClicked: (Long) -> Unit,
) {
    navigation<AppRoutes>(startDestination = AppRoutes.HomeRoute) {
        composable<AppRoutes.HomeRoute> {
            HomeScreen(
                onMovieClicked = onMovieClicked,
                onSeriesClicked = onSeriesClicked,
            )
        }
    }
}