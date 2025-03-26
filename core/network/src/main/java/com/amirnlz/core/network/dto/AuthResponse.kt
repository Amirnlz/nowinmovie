package com.amirnlz.core.network.dto


data class AuthResponse(
    val success: Boolean,
    val statusCode: Long,
    val statusMessage: String
)
