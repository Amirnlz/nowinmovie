package com.amirnlz.feature.movie_details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.ui.ErrorComponent
import com.amirnlz.core.ui.LoadingComponent


@Composable
fun MovieDetailRoute(
    modifier: Modifier = Modifier,
    movieId: Long,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onBackButtonPressed: () -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.onIntent(MovieDetailIntent.GetMovieDetails(movieId))
    }

    MovieDetailScreen(
        modifier = modifier, uiState = uiState,
        onRetry = {
            viewModel.onIntent(MovieDetailIntent.GetMovieDetails(movieId))
        },
        onBackButtonPressed = onBackButtonPressed
    )
}

@Composable
internal fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    uiState: MovieDetailUiState,
    onRetry: () -> Unit,
    onBackButtonPressed: () -> Unit,
) {

    when (uiState) {
        is MovieDetailUiState.Error -> ErrorComponent(
            message = uiState.error.message ?: "Error",
            onRetry = onRetry,
        )

        MovieDetailUiState.Loading -> LoadingComponent()
        is MovieDetailUiState.Success -> MovieDetailsContent(
            modifier,
            uiState.movieDetails,
            onBackButtonPressed
        )
    }
}


@Composable
private fun MovieDetailsContent(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails,
    onBackButtonPressed: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        content = { paddingValues ->
            MovieDetailsComponent(modifier = modifier.padding(paddingValues), movieDetails)
        }
    )
}

