package com.amirnlz.nowinmovie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.feature.auth.navigation.authScreen
import com.amirnlz.feature.auth.navigation.navigateToAuth
import com.amirnlz.feature.home.navigation.homeScreen
import com.amirnlz.feature.home.navigation.navigateToHome
import com.amirnlz.feature.splash.navigation.splashScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.SplashRoute, modifier = modifier
    ) {
        splashScreen(
            onNavigateToAuth = {
                navController.navigateToAuth(navOptions {
                    popUpTo(AppRoutes.AuthRoute) { inclusive = true }
                })
            },
            onNavigateToHome = {
                navController.navigateToHome(
                    navOptions { popUpTo(AppRoutes.HomeRoute) { inclusive = true } }
                )
            }
        )
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