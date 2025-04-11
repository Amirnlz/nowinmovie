package com.amirnlz.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.feature.auth.AuthScreenRoute

fun NavController.navigateToAuth(navOptions: NavOptions) =
  navigate(route = AppRoutes.AuthRoute, navOptions)

fun NavGraphBuilder.authScreen(onNavigateToHome: () -> Unit) {
  composable<AppRoutes.AuthRoute> {
    AuthScreenRoute(
      onNavigateToHome = { onNavigateToHome() },
    )
  }
}
