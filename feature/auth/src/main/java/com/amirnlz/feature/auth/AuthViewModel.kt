package com.amirnlz.feature.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    private val _effect = Channel<AuthEffect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.TokenChanged -> {
                _state.update { it.copy(token = intent.newToken, errorMessage = null) }
            }

            is AuthIntent.SubmitToken -> {
                authenticateToken(_state.value.token)
            }
        }
    }

    private fun authenticateToken(token: String) {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        // Simulate API call to validate the token
        viewModelScope.launch {
            // Replace this with your actual TMDB token validation logic
            val isValid = checkTmdbTokenValidity(token)
            _state.update { it.copy(isLoading = false) }
            if (isValid) {
                _state.update { it.copy(isAuthenticated = true) }
                _effect.send(AuthEffect.NavigateToHome)
            } else {
                _effect.send(AuthEffect.ShowError("Invalid TMDB Token"))
            }
        }
    }

    // Simulate checking TMDB token validity (replace with actual API call)
    private suspend fun checkTmdbTokenValidity(token: String): Boolean {
        // In a real application, you would make an API call to TMDB
        // to validate the token. For this example, we'll just check
        // if the token is not empty and has a certain length.
        kotlinx.coroutines.delay(1000) // Simulate network delay
        return token.isNotEmpty() && token.length > 10 // Example validation
    }
}