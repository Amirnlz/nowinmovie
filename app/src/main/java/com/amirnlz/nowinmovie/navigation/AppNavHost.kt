package com.amirnlz.nowinmovie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.feature.home.navigation.homeSection

@Composable
fun AppNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.HomeRoute, modifier = modifier
    ) {
        homeSection(
            onMovieClicked = {},
            onSeriesClicked = {},
        )

    }
}