package com.amirnlz.core.data.network.error

sealed class NetworkError(override val message: String? = null) : Throwable(message) {
    data object NoInternet : NetworkError("No internet connection")
    data object Timeout : NetworkError("Request timed out")
    data class ServerError(val code: Int, override val message: String?) :
        NetworkError("Server error occurred")

    data object Unauthorized : NetworkError("Unauthorized access")
    data class Unknown(override val message: String?) :
        NetworkError("An unknown network error occurred")
}