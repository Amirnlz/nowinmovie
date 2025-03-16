package com.amirnlz.core.domain.movie.repository

import com.amirnlz.core.domain.movie.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
    suspend fun getMovieDetails(movieId: Int): Result<Movie>
    suspend fun getFavoriteMovies(): Result<List<Movie>>
    suspend fun toggleFavoriteMovie(movieId: Int): Result<Unit>
}