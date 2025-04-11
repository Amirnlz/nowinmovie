package com.amirnlz.core.data.movie.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.amirnlz.core.data.movie.data_source.local.MovieLocalDataSource
import com.amirnlz.core.data.movie.data_source.remote.MoviePagingSource
import com.amirnlz.core.data.movie.data_source.remote.MovieRemoteDataSource
import com.amirnlz.core.data.movie.mapper.mapToMovie
import com.amirnlz.core.data.movie.mapper.mapToMovieCredits
import com.amirnlz.core.data.movie.mapper.mapToMovieDetails
import com.amirnlz.core.data.movie.mapper.mapToMovieEntity
import com.amirnlz.core.database.entity.MovieEntity
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.model.MovieCredits
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val DEFAULT_PAGE_SIZE = 20

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
) : MovieRepository {

    override suspend fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MoviePagingSource { page -> remoteDataSource.getTrendingMovies(page) }
            },
        ).flow
    }

    override suspend fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MoviePagingSource { page -> remoteDataSource.getPopularMovies(page) }
            },
        ).flow
    }

    override suspend fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MoviePagingSource { page -> remoteDataSource.getTopRatedMovies(page) }
            },
        ).flow
    }

    override suspend fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MoviePagingSource { page -> remoteDataSource.getUpcomingMovies(page) }
            },
        ).flow
    }

    override suspend fun getFavoriteMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { localDataSource.getFavoriteMovies() },
        ).flow
            .map { value: PagingData<MovieEntity> ->
                value.map { entity -> entity.mapToMovie() }
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

    override suspend fun getMovieRecommendations(movieId: Long): Result<List<Movie>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getMovieRecommendations(movieId = movieId)
                Result.success(response.results.map { it.mapToMovie() })
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun toggleFavoriteMovie(movieDetails: MovieDetails): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val isFavorite = localDataSource.getMovieFavoriteState(movieDetails.id)
                    .first()

                val newFavoriteState = if (isFavorite) {
                    localDataSource.deleteMovie(movieDetails.id)
                    false
                } else {
                    localDataSource.insertMovie(movieDetails.mapToMovieEntity())
                    true
                }

                Result.success(newFavoriteState)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getMovieFavoriteState(movieId: Long): Flow<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                localDataSource.getMovieFavoriteState(movieId)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getSearchMovie(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MoviePagingSource { page -> remoteDataSource.searchMovie(query, page) }
            },
        ).flow
    }
}
