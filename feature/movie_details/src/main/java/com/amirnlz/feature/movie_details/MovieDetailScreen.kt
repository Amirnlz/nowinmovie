package com.amirnlz.feature.movie_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amirnlz.core.ui.ErrorComponent
import com.amirnlz.core.ui.LoadingComponent
import com.amirnlz.feature.movie_detail.R
import com.amirnlz.feature.movie_details.component.MovieCreditsComponent
import com.amirnlz.feature.movie_details.component.MovieDetailsComponent


@Composable
fun MovieDetailRoute(
    modifier: Modifier = Modifier,
    movieId: Long,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onBackButtonPressed: () -> Unit,
) {
    val movieDetailsUiState by viewModel.movieDetailsState.collectAsStateWithLifecycle()
    val movieCreditsUiState by viewModel.movieCreditsState.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.onIntent(MovieDetailIntent.GetMovieDetails(movieId))
        viewModel.onIntent(MovieDetailIntent.GetMovieCredits(movieId))
    }

    MovieDetailScreen(
        modifier = modifier,
        movieDetails = movieDetailsUiState,
        movieCredits = movieCreditsUiState,
        onRetry = {
            viewModel.onIntent(MovieDetailIntent.GetMovieDetails(movieId))
            viewModel.onIntent(MovieDetailIntent.GetMovieCredits(movieId))
        },
        onBackButtonPressed = onBackButtonPressed
    )
}

@Composable
internal fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetailsUiState,
    movieCredits: MovieCreditsUiState,
    onRetry: () -> Unit,
    onBackButtonPressed: () -> Unit,
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
//                Movie Details
                    when (movieDetails) {
                        is MovieDetailsUiState.Error -> ErrorComponent(
                            message = movieDetails.error.message ?: "Error",
                            onRetry = onRetry,
                        )

                        MovieDetailsUiState.Loading -> LoadingComponent()
                        is MovieDetailsUiState.Success -> MovieDetailsComponent(
                            movieDetails = movieDetails.data
                        )
                    }
//                Movie Credits
                    when (movieCredits) {
                        is MovieCreditsUiState.Error -> ErrorComponent(
                            message = movieCredits.error.message ?: "Error",
                            onRetry = onRetry,
                        )

                        MovieCreditsUiState.Loading -> LoadingComponent()
                        is MovieCreditsUiState.Success -> MovieCreditsComponent(
                            movieCredits = movieCredits.data
                        )
                    }
                }
                Box(
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    IconButton(
                        onClick = onBackButtonPressed,
                    ) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

        }
    )
}