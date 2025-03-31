package com.amirnlz.feature.home

enum class MovieType { Trending, Popular, TopRated, Upcoming, Favorite }

sealed interface HomeIntent {
    data object TrendingMovies : HomeIntent
    data object PopularMovies : HomeIntent
    data object TopRatedMovies : HomeIntent
    data object UpcomingMovies : HomeIntent
    data object FavoriteMovies : HomeIntent
}

internal fun MovieType.toIntent(): HomeIntent = when (this) {
    MovieType.Trending -> HomeIntent.TrendingMovies
    MovieType.Popular -> HomeIntent.PopularMovies
    MovieType.TopRated -> HomeIntent.TopRatedMovies
    MovieType.Upcoming -> HomeIntent.UpcomingMovies
    MovieType.Favorite -> HomeIntent.FavoriteMovies
}

internal fun HomeIntent.toMovieType(): MovieType = when (this) {
    HomeIntent.TrendingMovies -> MovieType.Trending
    HomeIntent.PopularMovies -> MovieType.Popular
    HomeIntent.TopRatedMovies -> MovieType.TopRated
    HomeIntent.UpcomingMovies -> MovieType.Upcoming
    HomeIntent.FavoriteMovies -> MovieType.Favorite
}