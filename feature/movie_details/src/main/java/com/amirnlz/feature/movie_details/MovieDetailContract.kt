package com.amirnlz.feature.movie_details

import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.model.MovieCredits
import com.amirnlz.core.domain.movie.model.MovieDetails


data class MovieDetailsUiState(
    val movieDetails: MovieDetails? = null,
    val movieCredits: MovieCredits? = null,
    val movieRecommendations: List<Movie> = emptyList(),
    val favoriteState: Boolean = false,
    val loading: Boolean = false,
    val error: Throwable? = null
)

sealed interface MovieDetailIntent {
    data class ChangeMovieFavorite(val movieDetails: MovieDetails) : MovieDetailIntent
}
