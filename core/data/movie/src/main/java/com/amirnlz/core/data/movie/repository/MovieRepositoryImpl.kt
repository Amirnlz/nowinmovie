package com.amirnlz.core.data.movie.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amirnlz.core.data.movie.data_source.remote.MoviePagingSource
import com.amirnlz.core.data.movie.data_source.remote.MovieRemoteDataSource
import com.amirnlz.core.data.movie.mapper.mapToMovieCredits
import com.amirnlz.core.data.movie.mapper.mapToMovieDetails
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.model.MovieCredits
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


private const val DEFAULT_PAGE_SIZE = 20

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource { page -> remoteDataSource.getTrendingMovies(page) }
            }
        ).flow
    }

    override suspend fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource { page -> remoteDataSource.getPopularMovies(page) }
            }
        ).flow
    }


    override suspend fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource { page -> remoteDataSource.getTopRatedMovies(page) }
            }
        ).flow
    }

    override suspend fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource { page -> remoteDataSource.getUpcomingMovies(page) }
            }
        ).flow
    }

    override suspend fun getFavoriteMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource { page ->
                    remoteDataSource.getFavoriteMovies(
                        accountId = 1,
                        page = page
                    )
                }
            }
        ).flow
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