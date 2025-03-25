package com.amirnlz.nowinmovie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.feature.auth.navigation.authScreen
import com.amirnlz.feature.home.navigation.homeScreen
import com.amirnlz.feature.home.navigation.navigateToHome

@Composable
fun AppNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.AuthRoute, modifier = modifier
    ) {
        authScreen(
            onNavigateToHome = {
                navController.navigateToHome(navOptions = navOptions {
                    popUpTo(AppRoutes.AuthRoute) { inclusive = true }
                })
            },
        )
        homeScreen(
            onMovieClicked = {},
            onSeriesClicked = {},
        )

    }
}