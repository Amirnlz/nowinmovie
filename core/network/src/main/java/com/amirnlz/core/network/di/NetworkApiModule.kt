package com.amirnlz.core.network.di

import com.amirnlz.core.network.api.AuthApiService
import com.amirnlz.core.network.api.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkApiModule {

  @Provides
  @Singleton
  fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
    return retrofit.create(MovieApiService::class.java)
  }

  @Provides
  @Singleton
  fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
    return retrofit.create(AuthApiService::class.java)
  }
}
