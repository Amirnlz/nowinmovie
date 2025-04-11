package com.amirnlz.core.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorComponent(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp)
      .alpha(
        animateFloatAsState(
          targetValue = 1f,
          animationSpec = tween(500),
          label = "ErrorAlpha",
        ).value,
      ),
  ) {
    Icon(
      imageVector = Icons.Outlined.Close,
      contentDescription = "Error",
      tint = MaterialTheme.colorScheme.error,
      modifier = Modifier.size(48.dp),
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = message,
      color = MaterialTheme.colorScheme.onSurface,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = onRetry) {
      Text("Retry")
    }
  }
}
