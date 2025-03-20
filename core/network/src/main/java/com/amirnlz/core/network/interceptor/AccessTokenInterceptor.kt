package com.amirnlz.core.network.interceptor


import com.amirnlz.core.network.Authenticated
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Singleton

@Singleton
class AccessTokenInterceptor(
    private val accessToken: String = ""  //TODO: Refactor later and add TokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Check if the method has the @Authenticated annotation
        val invocation = request.tag(Invocation::class.java)
        val needsAuthentication =
            invocation?.method()?.getAnnotation(Authenticated::class.java) != null

        if (needsAuthentication) {
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
                // You might throw an exception or return an error response
                throw IllegalStateException("Access token required but not available.")
            }
        }

        return chain.proceed(request)
    }
}