package com.amirnlz.feature.movie_details

import com.amirnlz.core.domain.movie.model.MovieCredits
import com.amirnlz.core.domain.movie.model.MovieDetails

sealed interface UiState<T> {
    data object Loading : UiState<Nothing>
    data class Error(val error: Throwable) : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
}

sealed interface MovieDetailsUiState : UiState<MovieDetails> {
    data object Loading : MovieDetailsUiState
    data class Error(val error: Throwable) : MovieDetailsUiState
    data class Success(val data: MovieDetails) : MovieDetailsUiState
}

sealed interface MovieCreditsUiState : UiState<MovieCredits> {
    data object Loading : MovieCreditsUiState
    data class Error(val error: Throwable) : MovieCreditsUiState
    data class Success(val data: MovieCredits) : MovieCreditsUiState
}

sealed interface MovieDetailIntent {
    data class GetMovieDetails(val movieId: Long) : MovieDetailIntent
    data class GetMovieCredits(val movieId: Long) : MovieDetailIntent
}