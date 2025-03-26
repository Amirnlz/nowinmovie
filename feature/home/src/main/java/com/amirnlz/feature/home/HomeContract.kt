package com.amirnlz.feature.home

import com.amirnlz.core.domain.movie.model.MovieList

interface HomeContract {

    enum class MovieType { Trending, Popular, TopRated, Upcoming, Favorite }

    sealed interface HomeUiState {
        data object Loading : HomeUiState
        data class Error(val error: Throwable) : HomeUiState
        data class Success(val movies: MovieList) : HomeUiState
    }

    sealed interface HomeIntent {
        data object TrendingMovies : HomeIntent
        data object PopularMovies : HomeIntent
        data object TopRatedMovies : HomeIntent
        data object UpcomingMovies : HomeIntent
        data object FavoriteMovies : HomeIntent
    }

}

internal fun HomeContract.MovieType.toIntent(): HomeContract.HomeIntent {
    return when (this) {
        HomeContract.MovieType.Trending -> HomeContract.HomeIntent.TrendingMovies
        HomeContract.MovieType.Popular -> HomeContract.HomeIntent.PopularMovies
        HomeContract.MovieType.TopRated -> HomeContract.HomeIntent.TopRatedMovies
        HomeContract.MovieType.Upcoming -> HomeContract.HomeIntent.UpcomingMovies
        HomeContract.MovieType.Favorite -> HomeContract.HomeIntent.FavoriteMovies
    }
}

internal fun HomeContract.HomeIntent.toMovieType(): HomeContract.MovieType {
    return when (this) {
        HomeContract.HomeIntent.TrendingMovies -> HomeContract.MovieType.Trending
        HomeContract.HomeIntent.PopularMovies -> HomeContract.MovieType.Popular
        HomeContract.HomeIntent.TopRatedMovies -> HomeContract.MovieType.TopRated
        HomeContract.HomeIntent.UpcomingMovies -> HomeContract.MovieType.Upcoming
        HomeContract.HomeIntent.FavoriteMovies -> HomeContract.MovieType.Favorite
    }
}
