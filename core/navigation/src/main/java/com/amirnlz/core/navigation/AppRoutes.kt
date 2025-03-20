package com.amirnlz.core.navigation

import kotlinx.serialization.Serializable


sealed interface AppRoutes {
    @Serializable
    data object HomeRoute

    @Serializable
    data class MovieDetailsRoute(val movieId: Long)

    @Serializable
    data class SeriesDetailsRoute(val seriesId: Long)

    @Serializable
    data object BookmarksRoute

    @Serializable
    data object SearchRoute

    @Serializable
    data object SettingRoute
}