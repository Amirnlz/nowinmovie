package com.amirnlz.core.data.movie.repository

import com.amirnlz.core.data.movie.data_source.remote.MovieRemoteDataSource
import com.amirnlz.core.data.movie.mapper.mapToMovieDetails
import com.amirnlz.core.data.movie.mapper.mapToMovieList
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.model.MovieList
import com.amirnlz.core.domain.movie.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {
    override suspend fun getPopularMovies(): Result<MovieList> {
        return remoteDataSource.getPopularMovies()
            .map { it.mapToMovieList() }
    }

    override suspend fun getMovieDetails(movieId: Long): Result<MovieDetails> {
        return remoteDataSource.getMovieDetails(movieId = movieId)
            .map { it.mapToMovieDetails() }
    }

    override suspend fun getFavoriteMovies(): Result<MovieList> {
        return remoteDataSource.getFavoriteMovies(accountId = 1).map {
            it.mapToMovieList()
        }
    }

    override suspend fun toggleFavoriteMovie(movieId: Long): Result<Unit> {
        return remoteDataSource.addFavoriteMovie(accountId = 1, movieId)
    }
}