package com.amirnlz.core.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoutes {
  @Serializable
  data object SplashRoute : AppRoutes

  @Serializable
  data object AuthRoute : AppRoutes

  @Serializable
  data object HomeRoute : AppRoutes

  @Serializable
  data class MovieDetailsRoute(val movieId: Long) : AppRoutes

  @Serializable
  data object FavoritesRoute : AppRoutes

  @Serializable
  data object SearchRoute : AppRoutes

  @Serializable
  data object SettingRoute : AppRoutes
}
