package com.amirnlz.core.data.movie.data_source.remote

import com.amirnlz.core.network.api.MovieApiService
import com.amirnlz.core.network.dto.MovieDetailsDto
import com.amirnlz.core.network.dto.MovieListDto
import com.amirnlz.core.network.error.ErrorHandler
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) {

    suspend fun getPopularMovies(apiKey: String = ""): Result<MovieListDto> {
        return try {
            val response = movieApiService.getPopularMovies()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(ErrorHandler.handleException(e))
        }
    }

    suspend fun getMovieDetails(movieId: Long, apiKey: String = ""): Result<MovieDetailsDto> {
        return try {
            val response = movieApiService.getMovieDetails(movieId = movieId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(ErrorHandler.handleException(e))
        }
    }

    suspend fun getFavoriteMovies(accountId: Long): Result<MovieListDto> {
        return try {
            val response = movieApiService.getFavoriteMovies(accountId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(ErrorHandler.handleException(e))
        }
    }

    suspend fun addFavoriteMovie(accountId: Long, movieId: Long): Result<Unit> {
        return try {
            val response = movieApiService.addFavoriteMovie(accountId, movieId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(ErrorHandler.handleException(e))
        }
    }
}