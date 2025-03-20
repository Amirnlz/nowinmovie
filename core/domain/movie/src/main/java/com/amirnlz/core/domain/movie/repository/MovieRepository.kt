package com.amirnlz.core.domain.movie.repository

import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.model.MovieList

interface MovieRepository {
    suspend fun getPopularMovies(): Result<MovieList>
    suspend fun getMovieDetails(movieId: Long): Result<MovieDetails>
    suspend fun getFavoriteMovies(): Result<MovieList>
    suspend fun toggleFavoriteMovie(movieId: Long): Result<Unit>
}