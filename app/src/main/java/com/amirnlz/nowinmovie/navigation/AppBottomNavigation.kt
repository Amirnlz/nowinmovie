package com.amirnlz.nowinmovie.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.amirnlz.core.navigation.AppRoutes
import com.amirnlz.core.navigation.bottomNavItems

@Composable
fun AppBottomNavigation(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val notAllowedList = listOf(
        AppRoutes.SplashRoute,
        AppRoutes.AuthRoute,
        AppRoutes.MovieDetailsRoute,
        AppRoutes.SettingRoute
    )
    AnimatedVisibility(
        visible = currentDestination?.hierarchy?.any { it.hasRoute(AppRoutes.SplashRoute::class) } == false,
        enter = fadeIn() + expandVertically(animationSpec = tween(800)),
        exit = fadeOut() + shrinkOut(animationSpec = tween(800))
    ) {
        NavigationBar {
            bottomNavItems.forEach { item ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (item.enabled && !isSelected) {
                            navController.navigate(item.route)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )
                    },
                    label = {
                        Text(item.label)
                    },
                    enabled = item.enabled
                )
            }
        }
    }
}

fun NavController.navigate(route: AppRoutes) {
    val routeString = when (route) {
        AppRoutes.SplashRoute -> "splash"
        AppRoutes.AuthRoute -> "auth"
        AppRoutes.HomeRoute -> "home"
        is AppRoutes.MovieDetailsRoute -> "movieDetails/${route.movieId}"
        AppRoutes.FavoritesRoute -> "bookmarks"
        AppRoutes.SearchRoute -> "search"
        AppRoutes.SettingRoute -> "settings"
    }
    navigate(routeString) {
        launchSingleTop = true
        restoreState = true
    }
}

