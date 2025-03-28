package com.amirnlz.feature.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amirnlz.core.ui.ErrorComponent
import com.amirnlz.core.ui.LoadingComponent

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
    movieType: MovieType,
    onTabChanged: (MovieType) -> Unit,
    uiState: HomeUiState,
    onMovieClicked: (Long) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        HomeMovieTypeTabs(initialMovieType = movieType) { onTabChanged(it) }
        Spacer(Modifier.height(16.dp))
        when (uiState) {
            HomeUiState.Loading -> LoadingComponent()
            is HomeUiState.Error -> ErrorComponent(
                message = uiState.error.message ?: "Error", onRetry = { onTabChanged(movieType) })

            is HomeUiState.Success -> MovieComponent(
                movieList = uiState.movies,
                onMovieClicked = { onMovieClicked(it) }
            )
        }
    }
}

@Composable
private fun HomeMovieTypeTabs(
    modifier: Modifier = Modifier,
    initialMovieType: MovieType,
    onTabChanged: (MovieType) -> Unit,
) {
    var selectedMovieType by remember { mutableStateOf(initialMovieType) }
    val scrollState = rememberScrollState()
    val list = MovieType.entries.map { it.name }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Start
    ) {
        list.forEachIndexed { index, movieType ->
            val isSelected = index == selectedMovieType.ordinal
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
                            selectedMovieType = MovieType.entries[index]
                            onTabChanged(selectedMovieType)
                        }
                    )
            )
        }
    }
}

