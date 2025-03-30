package com.amirnlz.core.data.movie.repository

import com.amirnlz.core.data.movie.data_source.remote.MovieRemoteDataSource
import com.amirnlz.core.data.movie.mapper.mapToMovieCredits
import com.amirnlz.core.data.movie.mapper.mapToMovieDetails
import com.amirnlz.core.data.movie.mapper.mapToMovieList
import com.amirnlz.core.domain.movie.model.MovieCredits
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.model.MovieList
import com.amirnlz.core.domain.movie.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {
    override suspend fun getTrendingMovies(): Result<MovieList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getTrendingMovies()
                Result.success(response.mapToMovieList())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getPopularMovies(): Result<MovieList> = withContext(Dispatchers.IO) {
        try {
            val response = remoteDataSource.getPopularMovies()
            Result.success(response.mapToMovieList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTopRatedMovies(): Result<MovieList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getTopRatedMovies()
                Result.success(response.mapToMovieList())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getUpcomingMovies(): Result<MovieList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getUpcomingMovies()
                Result.success(response.mapToMovieList())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getFavoriteMovies(): Result<MovieList> {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    remoteDataSource.getFavoriteMovies(accountId = 11177714) //TODO: make it specific for user :))
                Result.success(response.mapToMovieList())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Long): Result<MovieDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getMovieDetails(movieId = movieId)
                Result.success(response.mapToMovieDetails())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getMovieCredits(movieId: Long): Result<MovieCredits> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getMovieCredits(movieId = movieId)
                Result.success(response.mapToMovieCredits())
            } catch (e: Exception) {
                Result.failure(e)
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
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}