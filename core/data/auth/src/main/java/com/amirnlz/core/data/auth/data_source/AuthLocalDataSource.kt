package com.amirnlz.core.data.auth.data_source

import com.amirnlz.core.secure_storage.AppSecureStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLocalDataSource @Inject constructor(private val secureStorage: AppSecureStorage) {

    suspend fun saveApiKey(apiKey: String) {
        try {
            secureStorage.saveAccessToken(apiKey)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun deleteAccessToken() {
        try {
            secureStorage.deleteAccessToken()
        } catch (e: Exception) {
            throw e
        }
    }
}
