package com.amirnlz.core.secure_storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccessTokenStorage @Inject constructor(@ApplicationContext context: Context) :
    AppSecureStorage {

    private val accessTokenKey = "access_token"
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveAccessToken(accessToken: String) {
        withContext(Dispatchers.IO) {
            try {
                sharedPreferences.edit().putString(accessTokenKey, accessToken).apply()

            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getAccessToken(): String? {
        return withContext(Dispatchers.IO) {
            try {
                sharedPreferences.getString(accessTokenKey, null)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun deleteAccessToken() {
        return withContext(Dispatchers.IO) {
            try {
                sharedPreferences.edit().remove(accessTokenKey).apply()
            } catch (e: Exception) {
                throw e
            }
        }
    }
}