package com.amirnlz.core.data.movie.data_source.remote

import com.amirnlz.core.network.api.MovieApiService
import com.amirnlz.core.network.dto.movie.MovieCreditsDto
import com.amirnlz.core.network.dto.movie.MovieDetailsDto
import com.amirnlz.core.network.dto.movie.MovieListDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) {

    suspend fun getPopularMovies(page: Int = 1): MovieListDto {
        try {
            return movieApiService.getPopularMovies(page = page)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getTrendingMovies(page: Int = 1): MovieListDto {
        return try {
            movieApiService.getTrendingMovies(page = page)
        } catch (e: Exception) {
            throw e

        }
    }

    suspend fun getUpcomingMovies(page: Int = 1): MovieListDto {
        return try {
            movieApiService.getUpcomingMovies(page = page)
        } catch (e: Exception) {
            throw e

        }
    }

    suspend fun getTopRatedMovies(page: Int = 1): MovieListDto {
        return try {
            movieApiService.getTopRatedMovies(page = page)
        } catch (e: Exception) {
            throw e

        }
    }

    suspend fun getMovieDetails(movieId: Long): MovieDetailsDto {
        return try {
            movieApiService.getMovieDetails(movieId = movieId)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getMovieCredits(movieId: Long): MovieCreditsDto {
        return try {
            movieApiService.getMovieCredits(movieId = movieId)
        } catch (e: Exception) {
            throw e
        }
    }
}