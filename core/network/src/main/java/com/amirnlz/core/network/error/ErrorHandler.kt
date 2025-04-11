package com.amirnlz.core.network.error

import com.amirnlz.core.data.network.error.NetworkError
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandler {
  fun handleException(throwable: Throwable): NetworkError {
    return when (throwable) {
      is SocketTimeoutException -> NetworkError.Timeout
      is UnknownHostException -> NetworkError.NoInternet
      is HttpException -> {
        when (throwable.code()) {
          401 -> NetworkError.Unauthorized
          in 500..599 -> NetworkError.ServerError(throwable.code(), throwable.message())
          else -> NetworkError.Unknown(throwable.message())
        }
      }

      else -> NetworkError.Unknown(throwable.message)
    }
  }
}
