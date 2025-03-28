package com.amirnlz.feature.movie_details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.feature.movie_details.MovieDetailRoute

fun NavController.navigateToMovieDetails(movieId: Long, navOptions: NavOptions? = null) {
    navigate(route = AppRoutes.MovieDetailsRoute(movieId), navOptions)
}


fun NavGraphBuilder.movieDetailsScreen(
    onBackButtonPressed: () -> Unit,
) {
    composable<AppRoutes.MovieDetailsRoute> { navBackStackEntry ->
        val movieDetailsRoute: AppRoutes.MovieDetailsRoute = navBackStackEntry.toRoute()
        MovieDetailRoute(
            movieId = movieDetailsRoute.movieId,
            onBackButtonPressed = onBackButtonPressed
        )
    }
}