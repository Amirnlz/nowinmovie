package com.amirnlz.feature.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.amirnlz.core.designsystem.component.ErrorComponent
import com.amirnlz.core.designsystem.component.LoadingComponent
import com.amirnlz.core.designsystem.component.movie.MovieGridList
import com.amirnlz.core.designsystem.theme.NowinmovieTheme
import com.amirnlz.core.domain.movie.model.Movie

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    onMovieClicked: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val lazyPagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val currentMovieType by viewModel.currentMovieType.collectAsState()

    HomeScreen(
        modifier = modifier,
        currentMovieType = currentMovieType,
        lazyPagingItems = lazyPagingItems,
        onMovieClicked = { onMovieClicked(it) },
        onTabChanged = {
            viewModel.onIntent(it.toIntent())
        }
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    currentMovieType: MovieType,
    onTabChanged: (MovieType) -> Unit,
    lazyPagingItems: LazyPagingItems<Movie>,
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
            HomeMovieTypeTabs(currentMovieType = currentMovieType) { onTabChanged(it) }
            MovieGridList(
                lazyPagingItems = lazyPagingItems,
                onMovieClicked = { onMovieClicked(it) }
            )
            lazyPagingItems.loadState.refresh.let { loadState ->
                when (loadState) {
                    is LoadState.Error -> ErrorComponent(
                        message = loadState.error.localizedMessage
                            ?: stringResource(R.string.an_error_occurred),
                        onRetry = { onTabChanged(currentMovieType) }
                    )

                    LoadState.Loading -> LoadingComponent()
                    is LoadState.NotLoading -> {
                        if (lazyPagingItems.itemCount == 0 && lazyPagingItems.loadState.append.endOfPaginationReached) {
                            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                                Text(stringResource(R.string.no_items_found))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeMovieTypeTabs(
    modifier: Modifier = Modifier,
    currentMovieType: MovieType,
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
            val isSelected = index == currentMovieType.ordinal
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
