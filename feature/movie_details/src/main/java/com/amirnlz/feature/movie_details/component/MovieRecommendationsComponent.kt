package com.amirnlz.feature.movie_details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amirnlz.core.designsystem.component.movie.MovieItem
import com.amirnlz.core.designsystem.theme.NowinmovieTheme
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.feature.movie_detail.R

@Composable
internal fun MovieRecommendationsComponent(
    modifier: Modifier = Modifier,
    movieList: List<Movie>,
    onMovieClicked: (Long) -> Unit,
) {
    if (movieList.isEmpty()) return
    Column(
        verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.medium)
    ) {
        Text(
            stringResource(R.string.recommendations),
            style = typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = colorScheme.onSurface,
            modifier = Modifier.padding(bottom = NowinmovieTheme.spacing.extraSmall)
        )
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.mediumSmall),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            items(movieList.size) { index ->
                val movie = movieList[index]
                MovieItem(
                    modifier = Modifier.size(height = 200.dp, width = 120.dp),
                    movie = movie,
                    onMovieClicked = onMovieClicked
                )
            }
        }

    }
}