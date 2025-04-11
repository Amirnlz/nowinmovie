package com.amirnlz.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
internal fun SearchRoute(viewModel: SearchViewModel = hiltViewModel(), onMovieClicked: (Long) -> Unit) {
  val query = viewModel.query.collectAsState()
  val lazyPagingItems = viewModel.searchMovies.collectAsLazyPagingItems()

  SearchScreen(
    query = query.value,
    onQueryChange = { viewModel.onIntent(SearchIntent.ChangeQuery(it)) },
    lazyPagingItems = lazyPagingItems,
    onMovieClicked = onMovieClicked,
  )
}

@Composable
private fun SearchScreen(query: String, onQueryChange: (String) -> Unit, onMovieClicked: (Long) -> Unit, lazyPagingItems: LazyPagingItems<Movie>) {
  Scaffold(
    modifier = Modifier
      .padding(NowinmovieTheme.dimens.screenPadding)
      .fillMaxSize(),
    topBar = {
      SearchTextField(
        query = query,
        onQueryChange = onQueryChange,
      )
    },
  ) { innerPadding ->
    Column(
      Modifier.padding(innerPadding),
      verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.large),
    ) {
      if (query.isNotEmpty()) {
        Spacer(modifier = Modifier.size(NowinmovieTheme.dimens.paddingMedium))
        MovieGridList(
          lazyPagingItems = lazyPagingItems,
          onMovieClicked = { onMovieClicked(it) },
        )
        lazyPagingItems.loadState.refresh.let { loadState ->
          when (loadState) {
            is LoadState.Error -> ErrorComponent(
              message = loadState.error.localizedMessage
                ?: stringResource(R.string.an_error_occurred),
              onRetry = { onQueryChange(query) },
            )

            LoadState.Loading -> LoadingComponent()
            is LoadState.NotLoading -> {
              if (lazyPagingItems.itemCount == 0 &&
                lazyPagingItems.loadState.append.endOfPaginationReached
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Text(stringResource(R.string.no_items_found))
                }
              }
            }
          }
        }
      } else {
        EmptyView()
      }
    }
  }
}

@Composable
private fun SearchTextField(modifier: Modifier = Modifier, query: String, onQueryChange: (String) -> Unit) {
  val keyboardController = LocalSoftwareKeyboardController.current
  val isClearIconVisible by remember { derivedStateOf { query.isNotEmpty() } }
  TextField(
    value = query,
    onValueChange = onQueryChange,
    modifier = modifier.fillMaxWidth(),
    placeholder = { Text(stringResource(R.string.search)) },
    shape = NowinmovieTheme.shapes.large,
    leadingIcon = {
      Icon(
        Icons.Rounded.Search,
        contentDescription = stringResource(R.string.search),
      )
    },
    suffix = {
      if (isClearIconVisible) {
        Icon(
          Icons.Rounded.Clear,
          contentDescription = stringResource(R.string.clear),
          modifier = Modifier.clickable {
            onQueryChange("")
          },
        )
      }
    },
    colors = TextFieldDefaults.colors(
      focusedIndicatorColor = Color.Transparent,
      unfocusedIndicatorColor = Color.Transparent,
      disabledIndicatorColor = Color.Transparent,
    ),
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Text,
      imeAction = ImeAction.Search,
      autoCorrectEnabled = false,
    ),
    keyboardActions = KeyboardActions { keyboardController?.hide() },
    singleLine = true,
  )
}

@Composable
fun EmptyView(modifier: Modifier = Modifier) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = modifier
      .fillMaxSize()
      .padding(NowinmovieTheme.spacing.large),
  ) {
    Icon(
      imageVector = Icons.Rounded.Search,
      contentDescription = "Search",
      modifier = Modifier
        .padding(NowinmovieTheme.spacing.large)
        .size(NowinmovieTheme.dimens.iconSizeExtraLarge),
      tint = MaterialTheme.colorScheme.primary,
    )
    Text(
      text = "Search for movies",
      style = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      ),
    )
  }
}
