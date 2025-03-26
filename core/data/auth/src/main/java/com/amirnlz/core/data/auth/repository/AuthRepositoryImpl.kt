package com.amirnlz.core.data.auth.repository

import com.amirnlz.core.data.auth.data_source.AuthLocalDataSource
import com.amirnlz.core.data.auth.data_source.AuthRemoteDataSource
import com.amirnlz.core.domain.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override suspend fun authenticateApiKey(apiKey: String): Result<String?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = authRemoteDataSource.authenticateApiKey(apiKey)
                authLocalDataSource.saveApiKey(apiKey)
                Result.success(response.statusMessage)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    }
}