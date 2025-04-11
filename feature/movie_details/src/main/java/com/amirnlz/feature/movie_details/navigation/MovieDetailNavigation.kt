package com.amirnlz.feature.movie_details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.feature.movie_details.MovieDetailRoute

fun NavController.navigateToMovieDetails(movieId: Long, navOptions: NavOptions? = null) {
    navigate(route = AppRoutes.MovieDetailsRoute(movieId), navOptions)
}


fun NavGraphBuilder.movieDetailsScreen(
    onBackButtonPressed: () -> Unit,
    onMovieClicked: (Long) -> Unit,
) {
    composable<AppRoutes.MovieDetailsRoute> {
        MovieDetailRoute(
            onBackButtonPressed = onBackButtonPressed,
            onMovieClicked = onMovieClicked,
        )
    }
}
