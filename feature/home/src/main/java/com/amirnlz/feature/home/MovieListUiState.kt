package com.amirnlz.feature.home

import com.amirnlz.core.domain.movie.model.MovieList

sealed interface MovieListUiState {
    data object Loading : MovieListUiState

    data class Error(val error: Throwable) : MovieListUiState

    data class Data(val movies: MovieList) : MovieListUiState
}