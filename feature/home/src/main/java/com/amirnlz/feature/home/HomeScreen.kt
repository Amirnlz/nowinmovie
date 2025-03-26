package com.amirnlz.feature.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    onMovieClicked: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()
    val movieType by viewModel.movieTypeState.collectAsState()

    LaunchedEffect(true) {
        viewModel.onIntent(movieType.toIntent())
    }

    HomeScreen(
        modifier = modifier,
        movieType = movieType,
        uiState = uiState,
        onMovieClicked = { onMovieClicked(it) },
        onTabChanged = {
            viewModel.onIntent(it.toIntent())
        }
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    movieType: HomeContract.MovieType,
    onTabChanged: (HomeContract.MovieType) -> Unit,
    uiState: HomeContract.HomeUiState,
    onMovieClicked: (Long) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        HomeMovieTypeTabs(selectedMovieType = movieType) { onTabChanged(it) }
        Spacer(Modifier.height(16.dp))
        when (uiState) {
            HomeContract.HomeUiState.Loading -> LoadingComponent()
            is HomeContract.HomeUiState.Error -> ErrorComponent(
                message = uiState.error.message ?: "Error", onRetry = { onTabChanged(movieType) })

            is HomeContract.HomeUiState.Success -> MovieComponent(movieList = uiState.movies)
        }
    }
}

@Composable
private fun HomeMovieTypeTabs(
    modifier: Modifier = Modifier,
    selectedMovieType: HomeContract.MovieType,
    onTabSelected: (HomeContract.MovieType) -> Unit,
) {
    var selectedIndex by remember {
        mutableIntStateOf(
            HomeContract.MovieType.entries.indexOf(
                selectedMovieType
            )
        )
    }
    val scrollState = rememberScrollState()
    val list = HomeContract.MovieType.entries.map { it.name }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Start
    ) {
        list.forEachIndexed { index, movieType ->
            val isSelected = index == selectedIndex
            val animatedColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.outline,
                animationSpec = tween(durationMillis = 300)
            )
            Text(
                movieType,
                color = animatedColor,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            selectedIndex = index
                        }
                    )
            )
        }
    }
}

@Composable
private fun LoadingComponent(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .alpha(
                animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(500),
                    label = "LoadingAlpha"
                ).value
            )
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ErrorComponent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    label = "ErrorAlpha"
                ).value
            )
    ) {
        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}


