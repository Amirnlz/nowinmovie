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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amirnlz.core.designsystem.theme.NowinmovieTheme
import com.amirnlz.core.domain.movie.model.MovieDetails
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieDetailScreen(
        modifier = modifier,
        uiState = uiState,
        onRetry = {
        },
        onBackButtonPressed = onBackButtonPressed,
        onMovieFavoriteChanged = { viewModel.onIntent(MovieDetailIntent.ChangeMovieFavorite(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    uiState: MovieDetailsUiState,
    onRetry: () -> Unit,
    onBackButtonPressed: () -> Unit,
    onMovieFavoriteChanged: (MovieDetails) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(
                        onClick = onBackButtonPressed,
                    ) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }, title = { Box {} })
        },
        content = { paddingValues ->
            if (uiState.loading) LoadingComponent() else Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = NowinmovieTheme.dimens.paddingExtraMedium)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.background)
            ) {
//                Movie Details
                MovieDetailsComponent(
                    movieDetails = uiState.movieDetails,
                    isMovieFavorite = uiState.favoriteState,
                    onMovieFavoriteChanged = onMovieFavoriteChanged,
                )

//                Movie Credits
                MovieCreditsComponent(
                    movieCredits = uiState.movieCredits
                )
            }
        }
    )
}