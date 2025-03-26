package com.amirnlz.feature.auth


import androidx.compose.runtime.Immutable

@Immutable
data class AuthState(
    val token: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAuthenticated: Boolean = false
)

sealed class AuthIntent {
    data class TokenChanged(val newToken: String) : AuthIntent()
    data object SubmitToken : AuthIntent()
}

sealed class AuthEffect {
    data object NavigateToHome : AuthEffect()
    data class ShowError(val message: String) : AuthEffect()
}