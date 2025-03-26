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