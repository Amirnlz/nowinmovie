package com.amirnlz.core.network.di

import com.amirnlz.core.network.BaseUrl
import com.amirnlz.core.network.BuildConfig
import com.amirnlz.core.network.interceptor.AccessTokenInterceptor
import com.amirnlz.core.secure_storage.AppSecureStorage
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideAccessTokenProvider(
        appSecureStorage: AppSecureStorage
    ): AccessTokenInterceptor {
        return AccessTokenInterceptor(appSecureStorage)
    }


    @Provides
    @Singleton
    fun provideAuthenticatedOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        accessTokenInterceptor: AccessTokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(accessTokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }


    @Provides
    @Singleton
    fun provideAuthenticatedRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        @BaseUrl authenticatedBaseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(authenticatedBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}