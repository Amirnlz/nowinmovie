package com.amirnlz.nowinmovie.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.amirnlz.core.navigation.topLevelRoutes


@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showNavigationBar = currentDestination?.hierarchy?.any {
        topLevelRoutes.any { topLevelRoute ->
            it.hasRoute(topLevelRoute.route::class)
        }
    } == true

    AnimatedVisibility(
        visible = showNavigationBar,
        enter = fadeIn() + expandVertically(animationSpec = tween(800)),
        exit = fadeOut() + shrinkVertically(animationSpec = tween(800)),
    ) {
        NavigationBar {
            topLevelRoutes.forEach { topLevelRoute ->
                NavigationBarItem(
                    enabled = topLevelRoute.enabled,
                    icon = { Icon(topLevelRoute.icon, contentDescription = topLevelRoute.name) },
                    label = { Text(topLevelRoute.name) },
                    selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true,
                    onClick = {
                        navController.navigate(topLevelRoute.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

            }
        }
    }
}