package com.amirnlz.feature.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
internal fun FavoritesRoute(
  modifier: Modifier = Modifier,
  onMovieClicked: (Long) -> Unit,
  viewModel: FavoritesViewModel = hiltViewModel(),
) {
  val lazyPagingItems = viewModel.uiState.collectAsLazyPagingItems()

  FavoritesScreen(
    modifier = modifier,
    onMovieClicked = onMovieClicked,
    lazyPagingItems = lazyPagingItems,
  )
}

@Composable
private fun FavoritesScreen(
  modifier: Modifier = Modifier,
  onMovieClicked: (Long) -> Unit,
  lazyPagingItems: LazyPagingItems<Movie>,
) {
  Scaffold(
    modifier = modifier
      .padding(NowinmovieTheme.dimens.screenPadding)
      .fillMaxSize(),
  ) { innerPadding ->
    Column(
      Modifier.padding(innerPadding),
    ) {
      MovieGridList(
        lazyPagingItems = lazyPagingItems,
        onMovieClicked = onMovieClicked,
      )
      lazyPagingItems.loadState.refresh.let { loadState ->
        when (loadState) {
          is LoadState.Error -> ErrorComponent(
            message = loadState.error.localizedMessage
              ?: stringResource(R.string.an_error_occurred),
            onRetry = { },
          )

          LoadState.Loading -> LoadingComponent()
          is LoadState.NotLoading -> {
            if (lazyPagingItems.itemCount == 0 &&
              lazyPagingItems.loadState.append.endOfPaginationReached
            ) {
              Box(contentAlignment = Alignment.Center) {
                Text(
                  stringResource(R.string.no_favorite_movies_found) +
                    stringResource(R.string.please_add_some_movies_to_your_favorites),
                  style = MaterialTheme.typography.titleLarge,
                  color = MaterialTheme.colorScheme.onBackground,
                )
              }
            }
          }
        }
      }
    }
  }
}
