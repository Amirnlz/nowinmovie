package com.amirnlz.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amirnlz.core.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
  @Query("SELECT * FROM movie")
  fun getFavoriteMovies(): PagingSource<Int, MovieEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMovie(movie: MovieEntity)

  @Query("DELETE FROM movie WHERE id = :id")
  suspend fun deleteMovie(id: Long)

  @Query("SELECT EXISTS(SELECT * FROM movie WHERE id = :id)")
  fun isMovieExist(id: Long): Flow<Boolean>

  @Query("SELECT COUNT(*) FROM movie")
  fun getFavoriteMovieCount(): Flow<Int>
}
