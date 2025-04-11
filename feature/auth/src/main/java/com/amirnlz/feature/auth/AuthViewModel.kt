package com.amirnlz.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirnlz.core.domain.auth.AuthenticateApiKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authenticateApiKeyUseCase: AuthenticateApiKeyUseCase) : ViewModel() {

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
    viewModelScope.launch {
      authenticateApiKeyUseCase(token).onSuccess {
        _state.update { it.copy(isLoading = false) }
        _state.update { it.copy(isAuthenticated = true, token = "") }
        _effect.send(AuthEffect.NavigateToHome)
      }.onFailure {
        _state.update { it.copy(isLoading = false, token = "") }
        _effect.send(AuthEffect.ShowError(it.message ?: "Invalid ApiKey."))
      }
    }
  }
}
