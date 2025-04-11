package com.amirnlz.core.secure_storage

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SecureStorageModule {

    @Binds
    @Singleton
    abstract fun bindAppSecureStorage(
        accessTokenStorage: AccessTokenStorage
    ): AppSecureStorage

}
