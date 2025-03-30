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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.amirnlz.core.designsystem.theme.NowinmovieTheme
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
    Surface(
        modifier = modifier
            .padding(NowinmovieTheme.dimens.screenPadding)
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.medium)
        ) {
            HomeMovieTypeTabs(selectedMovieType = movieType) { onTabChanged(it) }
            when (uiState) {
                HomeUiState.Loading -> LoadingComponent()
                is HomeUiState.Error -> ErrorComponent(
                    message = uiState.error.message ?: "Error",
                    onRetry = { onTabChanged(movieType) })

                is HomeUiState.Success -> MovieComponent(
                    movieList = uiState.movies,
                    onMovieClicked = { onMovieClicked(it) }
                )
            }
        }
    }
}

@Composable
private fun HomeMovieTypeTabs(
    modifier: Modifier = Modifier,
    selectedMovieType: MovieType,
    onTabChanged: (MovieType) -> Unit,
) {
    val list = MovieType.entries.map { it.name }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.small),
    ) {
        list.forEachIndexed { index, movieType ->
            val isSelected = index == selectedMovieType.ordinal
            val animatedColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.onSurfaceVariant
                else MaterialTheme.colorScheme.outline,
                animationSpec = tween(durationMillis = 300)
            )
            val animatedBackground by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.surfaceVariant
                else MaterialTheme.colorScheme.background,
                animationSpec = tween(durationMillis = 300)
            )

            Text(
                movieType,
                color = animatedColor,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(animatedBackground)
                    .padding(NowinmovieTheme.dimens.screenPadding)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            onTabChanged(MovieType.entries[index])
                        }
                    )
            )
        }
    }
}
