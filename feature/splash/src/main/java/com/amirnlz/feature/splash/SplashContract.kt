package com.amirnlz.feature.splash

interface SplashContract {
  sealed class UiState {
    data object Loading : UiState()
  }

  sealed class Effect {
    data object NavigateToHome : Effect()
    data object NavigateToAuth : Effect()
  }
}
