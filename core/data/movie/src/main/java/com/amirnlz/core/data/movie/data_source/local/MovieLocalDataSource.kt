package com.amirnlz.core.data.movie.data_source.local

import androidx.paging.PagingSource
import com.amirnlz.core.database.dao.FavoriteMovieDao
import com.amirnlz.core.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalDataSource @Inject constructor(private val favoriteMovieDao: FavoriteMovieDao) {

    fun getFavoriteMovies(): PagingSource<Int, MovieEntity> {
        try {
            return favoriteMovieDao.getFavoriteMovies()
        } catch (e: Exception) {
            throw e
        }
    }

    fun getMovieFavoriteState(id: Long): Flow<Boolean> {
        try {
            return favoriteMovieDao.isMovieExist(id)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun insertMovie(movie: MovieEntity) {
        try {
            favoriteMovieDao.insertMovie(movie)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun deleteMovie(id: Long) {
        try {
            favoriteMovieDao.deleteMovie(id)
        } catch (e: Exception) {
            throw e
        }
    }

    fun getFavoriteMovieCount(): Flow<Int> {
        try {
            return favoriteMovieDao.getFavoriteMovieCount()
        } catch (e: Exception) {
            throw e
        }
    }

}
