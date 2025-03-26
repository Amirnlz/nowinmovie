package com.amirnlz.core.secure_storage

interface AppSecureStorage {
    suspend fun saveAccessToken(accessToken: String)
    suspend fun hasToken(): Boolean
    suspend fun getAccessToken(): String?
    suspend fun deleteAccessToken()
}