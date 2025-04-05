package com.amirnlz.nowinmovie.di

import com.amirnlz.core.data.auth.repository.AuthRepositoryImpl
import com.amirnlz.core.data.movie.repository.MovieRepositoryImpl
import com.amirnlz.core.data.search.repository.SearchRepositoryImpl
import com.amirnlz.core.domain.auth.AuthRepository
import com.amirnlz.core.domain.movie.repository.MovieRepository
import com.amirnlz.core.domain.search.repository.SearchRepository
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

    @Binds
    @Singleton
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun provideSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}