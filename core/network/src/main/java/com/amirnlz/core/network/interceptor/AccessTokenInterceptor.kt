package com.amirnlz.core.network.interceptor


import com.amirnlz.core.network.Authenticated
import com.amirnlz.core.secure_storage.AppSecureStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val secureStorage: AppSecureStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Check if the method has the @Authenticated annotation
        val invocation = request.tag(Invocation::class.java)
        val needsAuthentication =
            invocation?.method()?.getAnnotation(Authenticated::class.java) != null

        if (needsAuthentication) {
            // Retrieve the access token synchronously
            val accessToken = runBlocking { secureStorage.getAccessToken() }

            if (!accessToken.isNullOrBlank()) {
                val newRequest = request.newBuilder()
                    .header(
                        "Authorization",
                        "Bearer $accessToken"
                    ) // Or your preferred authorization header
                    .build()
                return chain.proceed(newRequest)
            } else {
                // Handle the case where authentication is required but no token is available
                // This could involve throwing an exception, returning an error response,
                // or redirecting the user to the login screen.
                throw IllegalStateException("Access token required for this request but is not available.")
            }
        }

        return chain.proceed(request)
    }
}