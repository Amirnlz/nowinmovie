package com.amirnlz.core.domain.movie

import com.amirnlz.core.domain.movie.repository.MovieRepository
import com.amirnlz.core.domain.movie.usecase.GetFavoriteMoviesUseCase
import com.amirnlz.core.domain.movie.usecase.GetMovieDetailsUseCase
import com.amirnlz.core.domain.movie.usecase.GetPopularMoviesUseCase
import com.amirnlz.core.domain.movie.usecase.ToggleFavoriteMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDomainModule {

    @Singleton
    @Provides
    fun provideGetFavoriteMovieUseCase(repository: MovieRepository): GetFavoriteMoviesUseCase {
        return GetFavoriteMoviesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetMovieDetailsUseCase(repository: MovieRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetPopularMovieUseCase(repository: MovieRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideToggleFavoriteMovieUseCase(repository: MovieRepository): ToggleFavoriteMovieUseCase {
        return ToggleFavoriteMovieUseCase(repository)
    }
}