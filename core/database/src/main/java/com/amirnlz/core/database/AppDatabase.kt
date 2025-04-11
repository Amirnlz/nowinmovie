package com.amirnlz.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amirnlz.core.database.dao.FavoriteMovieDao
import com.amirnlz.core.database.entity.MovieEntity
import com.amirnlz.core.database.util.Converters

@Database(
  entities = [MovieEntity::class],
  version = 1,
  exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
  abstract fun favoriteMovieDao(): FavoriteMovieDao
}
