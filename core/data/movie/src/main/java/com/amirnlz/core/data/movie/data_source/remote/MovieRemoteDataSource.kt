package com.amirnlz.core.data.movie.data_source.remote

import com.amirnlz.core.network.api.MovieApiService
import com.amirnlz.core.network.dto.MovieDetailsDto
import com.amirnlz.core.network.dto.MovieListDto
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) {

    suspend fun getPopularMovies(apiKey: String = ""): MovieListDto {
        try {
            return movieApiService.getPopularMovies()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getMovieDetails(movieId: Long, apiKey: String = ""): MovieDetailsDto {
        return try {
            movieApiService.getMovieDetails(movieId = movieId)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getFavoriteMovies(accountId: Long): MovieListDto {
        return try {
            movieApiService.getFavoriteMovies(accountId)
        } catch (e: Exception) {
            throw e

        }
    }

    suspend fun addFavoriteMovie(accountId: Long, movieId: Long): Unit {
        return try {
            movieApiService.addFavoriteMovie(accountId, movieId)
        } catch (e: Exception) {
            throw e
        }
    }
}