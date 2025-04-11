package com.amirnlz.core.network.interceptor

import com.amirnlz.core.network.Authenticated
import com.amirnlz.core.secure_storage.AppSecureStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
  private val secureStorage: AppSecureStorage,
) : Interceptor {

  private val authorizationHeaderName = "Authorization"
  private val bearerTokenPrefix = "Bearer "
  private val apiKeyQueryParameterName = "api_key"

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()

    return when {
      !isRequestAuthenticated(request) -> chain.proceed(request)
      hasApiKeyInQuery(request) -> proceedWithApiKey(chain, request)
      else -> proceedWithAccessToken(chain, request)
    }
  }

  private fun isRequestAuthenticated(request: Request): Boolean {
    return request.tag(Invocation::class.java)
      ?.method()
      ?.getAnnotation(Authenticated::class.java) != null
  }

  private fun hasApiKeyInQuery(request: Request): Boolean {
    return request.url.queryParameter(apiKeyQueryParameterName) != null
  }

  private fun proceedWithApiKey(chain: Interceptor.Chain, request: Request): Response {
    val apiKey = requireNotNull(request.url.queryParameter(apiKeyQueryParameterName))
    return chain.proceed(
      request.newBuilder()
        .header(authorizationHeaderName, "$bearerTokenPrefix $apiKey")
        .build(),
    )
  }

  private fun proceedWithAccessToken(chain: Interceptor.Chain, request: Request): Response {
    val accessToken = runBlocking { secureStorage.getAccessToken() }
      ?: throw IllegalStateException("Access token required but not available")

    return chain.proceed(
      request.newBuilder()
        .header(authorizationHeaderName, "$bearerTokenPrefix $accessToken")
        .build(),
    )
  }
}
