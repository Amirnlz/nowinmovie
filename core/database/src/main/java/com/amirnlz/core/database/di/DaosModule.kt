package com.amirnlz.core.database.di

import com.amirnlz.core.database.AppDatabase
import com.amirnlz.core.database.dao.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

  @Provides
  fun providesFavoriteMovieDao(database: AppDatabase): FavoriteMovieDao = database.favoriteMovieDao()
}
