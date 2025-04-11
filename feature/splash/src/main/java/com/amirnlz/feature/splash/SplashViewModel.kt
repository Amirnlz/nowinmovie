package com.amirnlz.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirnlz.core.secure_storage.AppSecureStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val secureStorage: AppSecureStorage) : ViewModel() {

  private val _uiState = MutableStateFlow<SplashContract.UiState>(SplashContract.UiState.Loading)
  val uiState: StateFlow<SplashContract.UiState> = _uiState.asStateFlow()

  private val _effect = MutableSharedFlow<SplashContract.Effect>()
  val effect: SharedFlow<SplashContract.Effect> = _effect.asSharedFlow()

  init {
    checkToken()
  }

  private fun checkToken() {
    viewModelScope.launch {
      val hasToken = secureStorage.hasToken()
      delay(2_000)
      val emitValue = when (hasToken) {
        true -> SplashContract.Effect.NavigateToHome
        false -> SplashContract.Effect.NavigateToAuth
      }
      _effect.emit(emitValue)
    }
  }
}
