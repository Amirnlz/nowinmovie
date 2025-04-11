package com.amirnlz.core.domain.auth

import javax.inject.Inject

class AuthenticateApiKeyUseCase @Inject constructor(private val repository: AuthRepository) {

  suspend operator fun invoke(apiKey: String): Result<String?> = repository.authenticateApiKey(apiKey)
}
