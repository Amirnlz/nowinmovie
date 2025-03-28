package com.amirnlz.feature.movie_details

import com.amirnlz.core.domain.movie.model.MovieDetails

sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState
    data class Error(val error: Throwable) : MovieDetailUiState
    data class Success(val movieDetails: MovieDetails) : MovieDetailUiState
}

sealed interface MovieDetailIntent {
    data class GetMovieDetails(val movieId: Long) : MovieDetailIntent
}