package com.amirnlz.core.data.auth.data_source

import com.amirnlz.core.network.api.AuthApiService
import com.amirnlz.core.network.dto.auth.AuthResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(private val authApiService: AuthApiService) {

    suspend fun authenticateApiKey(apiKey: String): AuthResponse {
        try {
            return authApiService.authenticateApiKey(apiKey)
        } catch (e: Exception) {
            throw e
        }
    }
}