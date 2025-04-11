package com.amirnlz.feature.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthScreenRoute(
  modifier: Modifier = Modifier,
  viewModel: AuthViewModel = hiltViewModel(),
  onNavigateToHome: () -> Unit,
) {
  val state = viewModel.state.collectAsState().value
  val context = LocalContext.current

  LaunchedEffect(viewModel.effect) {
    viewModel.effect.collectLatest { effect ->
      when (effect) {
        is AuthEffect.NavigateToHome -> onNavigateToHome()
        is AuthEffect.ShowError -> {
          Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  AuthScreen(
    modifier = modifier,
    state = state,
    onTokenChanged = { viewModel.onIntent(AuthIntent.TokenChanged(it)) },
    onSubmitClick = { viewModel.onIntent(AuthIntent.SubmitToken) },
  )
}

@Composable
private fun AuthScreen(
  modifier: Modifier,
  state: AuthState,
  onTokenChanged: (String) -> Unit,
  onSubmitClick: () -> Unit,
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = stringResource(R.string.auth_title),
      style = MaterialTheme.typography.headlineLarge,
      modifier = Modifier.padding(bottom = 16.dp),
    )

    TokenInputField(
      value = state.token,
      onValueChange = onTokenChanged,
      errorMessage = state.errorMessage,
    )
    Spacer(modifier = Modifier.height(16.dp))

    SubmitButton(
      isLoading = state.isLoading,
      onClick = onSubmitClick,
    )
  }
}

@Composable
private fun TokenInputField(
  value: String,
  onValueChange: (String) -> Unit,
  errorMessage: String?,
) {
  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(stringResource(R.string.tmdb_token)) },
    singleLine = true,
    modifier = Modifier.fillMaxWidth(),
    visualTransformation = PasswordVisualTransformation(),
    isError = errorMessage != null,
    supportingText = {
      if (errorMessage != null) {
        Text(
          text = errorMessage,
          color = MaterialTheme.colorScheme.error,
        )
      }
    },
  )
}

@Composable
private fun SubmitButton(isLoading: Boolean, onClick: () -> Unit) {
  Button(
    onClick = onClick,
    enabled = !isLoading,
  ) {
    if (isLoading) {
      CircularProgressIndicator(modifier = Modifier.size(24.dp))
      Spacer(modifier = Modifier.width(8.dp))
      Text(stringResource(R.string.authenticating))
    } else {
      Text(stringResource(R.string.submit))
    }
  }
}
