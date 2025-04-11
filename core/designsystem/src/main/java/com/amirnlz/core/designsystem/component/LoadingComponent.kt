package com.amirnlz.core.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun LoadingComponent(modifier: Modifier = Modifier) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp)
      .alpha(
        animateFloatAsState(
          targetValue = 1f,
          animationSpec = tween(500),
          label = "LoadingAlpha",
        ).value,
      ),
  ) {
    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
  }
}
