package com.amirnlz.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.feature.splash.SplashScreenRoute

fun NavController.navigateToSplash(navOptions: NavOptions) =
    navigate(route = AppRoutes.SplashRoute, navOptions)

fun NavGraphBuilder.splashScreen(onNavigateToHome: () -> Unit, onNavigateToAuth: () -> Unit) {
    composable<AppRoutes.SplashRoute> {
        SplashScreenRoute(
            onNavigateToHome = { onNavigateToHome() },
            onNavigateToAuth = { onNavigateToAuth() }
        )
    }
}
