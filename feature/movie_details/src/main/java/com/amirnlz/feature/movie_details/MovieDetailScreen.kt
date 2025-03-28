package com.amirnlz.feature.movie_details

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.ui.ImageNetwork
import com.amirnlz.feature.movie_detail.R


@Composable
fun MovieDetailRoute(
    modifier: Modifier = Modifier,
    movieId: Long,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.onIntent(MovieDetailIntent.GetMovieDetails(movieId))
    }

    MovieDetailScreen(modifier = modifier, uiState = uiState, onRetry = {
        viewModel.onIntent(MovieDetailIntent.GetMovieDetails(movieId))
    })
}

@Composable
internal fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    uiState: MovieDetailUiState,
    onRetry: () -> Unit
) {

    when (uiState) {
        is MovieDetailUiState.Error -> {
            Log.e("TAG", "MovieDetailScreen: ${uiState.error.message}", uiState.error)
            ErrorComponent(
                message = uiState.error.message ?: "Error",
                onRetry = onRetry,
                modifier = modifier
            )
        }

        MovieDetailUiState.Loading -> LoadingComponent()
        is MovieDetailUiState.Success -> MovieDetailsContent(uiState.movieDetails)
    }
}


@Composable
fun MovieDetailsContent(movieDetails: MovieDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header Section: Backdrop and Poster
        MovieHeader(
            backdropPath = movieDetails.backdropPath,
            posterPath = movieDetails.posterPath,
            title = movieDetails.title
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Overview Section
        Text(
            text = movieDetails.overview,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        // Details Section
        MovieDetailsRow(
            label = stringResource(R.string.release_date),
            value = movieDetails.releaseDate
        )
        MovieDetailsRow(
            label = stringResource(R.string.runtime),
            value = "${movieDetails.runtime} min"
        )
        MovieDetailsRow(label = stringResource(R.string.status), value = movieDetails.status)
        MovieDetailsRow(
            label = stringResource(R.string.vote_average),
            value = "${movieDetails.voteAverage / 10.0}"
        ) // Assuming voteAverage is out of 100
        MovieDetailsRow(
            label = stringResource(R.string.vote_count),
            value = movieDetails.voteCount.toString()
        )
        MovieDetailsRow(label = stringResource(R.string.budget), value = "${movieDetails.budget}")
        MovieDetailsRow(label = stringResource(R.string.revenue), value = "${movieDetails.revenue}")

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        // Genres
        if (movieDetails.genres.isNotEmpty()) {
            Text(
                text = stringResource(R.string.genres),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Row {
                movieDetails.genres.forEach { genre ->
                    Text(
                        text = genre.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Spoken Languages
        if (movieDetails.spokenLanguages.isNotEmpty()) {
            Text(
                text = stringResource(R.string.spoken_languages),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Column {
                movieDetails.spokenLanguages.forEach { language ->
                    Text(
                        text = language.englishName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Tagline
        if (movieDetails.tagline.isNotBlank()) {
            Text(
                text = stringResource(R.string.tagline),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = movieDetails.tagline,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun MovieHeader(backdropPath: String, posterPath: String, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Backdrop Image
        ImageNetwork(
            imagePath = backdropPath,
            contentDescription = stringResource(R.string.backdrop_image_description, title),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f) // Standard aspect ratio for backdrops
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Poster and Title
        Row(verticalAlignment = Alignment.CenterVertically) {
            ImageNetwork(
                imagePath = posterPath,
                contentDescription = stringResource(R.string.poster_image_description, title),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(120.dp)
                    .aspectRatio(2f / 3f) // Standard aspect ratio for posters
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun MovieDetailsRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(120.dp) // Adjust width as needed
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
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