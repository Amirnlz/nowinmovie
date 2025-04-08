package com.amirnlz.core.network.dto.auth


data class AuthResponse(
    val success: Boolean,
    val statusCode: Long,
    val statusMessage: String
)
