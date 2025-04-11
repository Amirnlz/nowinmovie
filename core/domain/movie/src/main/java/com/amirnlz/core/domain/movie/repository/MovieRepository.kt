package com.amirnlz.core.domain.movie.repository

import androidx.paging.PagingData
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.model.MovieCredits
import com.amirnlz.core.domain.movie.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrendingMovies(): Flow<PagingData<Movie>>
    suspend fun getPopularMovies(): Flow<PagingData<Movie>>
    suspend fun getTopRatedMovies(): Flow<PagingData<Movie>>
    suspend fun getUpcomingMovies(): Flow<PagingData<Movie>>
    suspend fun getFavoriteMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(movieId: Long): Result<MovieDetails>
    suspend fun getMovieCredits(movieId: Long): Result<MovieCredits>
    suspend fun getMovieRecommendations(movieId: Long): Result<List<Movie>>
    suspend fun toggleFavoriteMovie(movieDetails: MovieDetails): Result<Boolean>
    suspend fun getMovieFavoriteState(movieId: Long): Flow<Boolean>
    suspend fun getSearchMovie(query: String): Flow<PagingData<Movie>>
}
