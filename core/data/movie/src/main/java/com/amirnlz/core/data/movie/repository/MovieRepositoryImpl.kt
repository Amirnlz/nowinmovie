package com.amirnlz.core.data.movie.repository

import com.amirnlz.core.data.movie.data_source.remote.MovieRemoteDataSource
import com.amirnlz.core.data.movie.mapper.mapToMovieDetails
import com.amirnlz.core.data.movie.mapper.mapToMovieList
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.model.MovieList
import com.amirnlz.core.domain.movie.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {
    override suspend fun getPopularMovies(): Result<MovieList> = withContext(Dispatchers.IO) {
        try {
            val response = remoteDataSource.getPopularMovies()
            return@withContext Result.success(response.mapToMovieList())
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    override suspend fun getMovieDetails(movieId: Long): Result<MovieDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getMovieDetails(movieId = movieId)
                return@withContext Result.success(response.mapToMovieDetails())
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun getFavoriteMovies(): Result<MovieList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getFavoriteMovies(accountId = 1)
                return@withContext Result.success(response.mapToMovieList())
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

    override suspend fun toggleFavoriteMovie(movieId: Long): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.addFavoriteMovie(
                    accountId = 1,
                    movieId = movieId
                )
                return@withContext Result.success(response)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }
}