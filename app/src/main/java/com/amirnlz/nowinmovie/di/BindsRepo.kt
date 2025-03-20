package com.amirnlz.nowinmovie.di

import com.amirnlz.core.data.movie.repository.MovieRepositoryImpl
import com.amirnlz.core.domain.movie.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsRepo {

    @Binds
    @Singleton
    abstract fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}