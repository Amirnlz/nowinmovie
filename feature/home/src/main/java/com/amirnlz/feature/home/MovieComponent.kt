package com.amirnlz.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.amirnlz.core.designsystem.theme.NowinmovieTheme
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.designsystem.component.ErrorComponent
import com.amirnlz.core.designsystem.component.ImageNetwork
import com.amirnlz.core.designsystem.component.LoadingComponent
import java.util.Locale

@Composable
internal fun MovieComponent(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<Movie>,
    onMovieClicked: (Long) -> Unit,
) {
    LazyVerticalGrid(
        state = rememberLazyGridState(),
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.medium)
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id },
            contentType = lazyPagingItems.itemContentType { "itemType" }
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
                        onRetry = {}
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


@Composable
private fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClicked: (Long) -> Unit,
) {
    Card(
        onClick = { onMovieClicked(movie.id) },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(NowinmovieTheme.dimens.paddingExtraMedium)
        ) {
            ImageNetwork(
                imagePath = movie.posterPath ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(NowinmovieTheme.dimens.posterAspectRatio)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.height(NowinmovieTheme.spacing.small))
            Column(
                verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.small)
            ) {
                Text(
                    text = movie.title ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    movie.releaseDate?.let { date ->
                        Text(
                            text = date.year.toString(),
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        )
                    }
                    StarRating(rating = movie.voteAverage ?: 0.0)

                }
            }
        }
    }
}

@Composable
fun StarRating(
    rating: Double,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Rating Star",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp)
        )

        Text(
            String.format(Locale.getDefault(), "%.1f", rating),
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
        )
    }
}

