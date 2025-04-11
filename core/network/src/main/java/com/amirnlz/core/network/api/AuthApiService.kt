package com.amirnlz.core.network.api

import com.amirnlz.core.network.Authenticated
import com.amirnlz.core.network.dto.auth.AuthResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApiService {
  @GET("/3/authentication")
  @Authenticated
  suspend fun authenticateApiKey(@Query("api_key") apiKey: String): AuthResponse
}
