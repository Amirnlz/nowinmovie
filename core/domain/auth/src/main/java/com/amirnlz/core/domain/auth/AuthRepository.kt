package com.amirnlz.core.domain.auth

interface AuthRepository {

  suspend fun authenticateApiKey(apiKey: String): Result<String?>
}
