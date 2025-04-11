package com.amirnlz.core.designsystem.component.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.amirnlz.core.designsystem.component.ErrorComponent
import com.amirnlz.core.designsystem.component.LoadingComponent
import com.amirnlz.core.designsystem.theme.NowinmovieTheme
import com.amirnlz.core.domain.movie.model.Movie

@Composable
fun MovieGridList(modifier: Modifier = Modifier, lazyPagingItems: LazyPagingItems<Movie>, onMovieClicked: (Long) -> Unit) {
  LazyVerticalGrid(
    state = rememberLazyGridState(),
    columns = GridCells.Fixed(2),
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.medium),
    horizontalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.medium),
  ) {
    items(
      count = lazyPagingItems.itemCount,
      key = lazyPagingItems.itemKey { it.id },
      contentType = lazyPagingItems.itemContentType { "itemType" },
    ) { index ->
      val movie = lazyPagingItems[index]
      if (movie != null) {
        MovieItem(movie = movie) { onMovieClicked(it) }
      } else {
        // PlaceholderItem()
      }
    }

    lazyPagingItems.loadState.append.let { loadState ->
      when (loadState) {
        is LoadState.Error -> item {
          ErrorComponent(
            message = loadState.error.localizedMessage ?: "Could not load more items",
            onRetry = {},
          )
        }

        LoadState.Loading -> item {
          LoadingComponent()
        }

        is LoadState.NotLoading -> item {}
      }
    }
  }
}
