package com.amirnlz.core.domain.movie.repository

import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.model.MovieList

interface MovieRepository {
    suspend fun getTrendingMovies(): Result<MovieList>
    suspend fun getPopularMovies(): Result<MovieList>
    suspend fun getTopRatedMovies(): Result<MovieList>
    suspend fun getUpcomingMovies(): Result<MovieList>
    suspend fun getFavoriteMovies(): Result<MovieList>
    suspend fun getMovieDetails(movieId: Long): Result<MovieDetails>
    suspend fun toggleFavoriteMovie(movieId: Long): Result<Unit>
}