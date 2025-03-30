package com.amirnlz.feature.movie_details.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.amirnlz.core.designsystem.theme.NowinmovieTheme
import com.amirnlz.core.domain.movie.model.MovieCast
import com.amirnlz.core.domain.movie.model.MovieCredits
import com.amirnlz.core.ui.ImageNetwork
import com.amirnlz.feature.movie_detail.R

@Composable
internal fun MovieCreditsComponent(modifier: Modifier = Modifier, movieCredits: MovieCredits) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val list = listOf(stringResource(R.string.cast), stringResource(R.string.director_crew))

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.small)
    ) {
        TabsComponent(selectedIndex = selectedIndex, list = list) { selectedIndex = it }
        Crossfade(
            targetState = selectedIndex,
            animationSpec = tween(300)
        ) { screen ->
            when (screen) {
                0 -> CastComponent(casts = movieCredits.cast)
                else -> CastComponent(casts = movieCredits.crew)
            }
        }
    }
}

@Composable
private fun TabsComponent(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    list: List<String>,
    onClick: (Int) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = colorScheme.surfaceVariant,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium),
        indicator = { Box {} }
    ) {
        list.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex
            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) colorScheme.background
                else colorScheme.surfaceVariant,
                animationSpec = tween(durationMillis = 300)
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) colorScheme.onSurfaceVariant
                else colorScheme.onSurface,
                animationSpec = tween(durationMillis = 300)
            )

            Tab(
                selected = isSelected,
                onClick = { onClick(index) },
                modifier = Modifier
                    .padding(NowinmovieTheme.spacing.small)
                    .clip(MaterialTheme.shapes.medium)
                    .background(backgroundColor)
                    .padding(
                        vertical = NowinmovieTheme.dimens.paddingExtraMedium,
                        horizontal = NowinmovieTheme.dimens.paddingSmall,
                    ),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                )
            }
        }
    }
}

@Composable
private fun CastComponent(casts: List<MovieCast>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.medium)
    ) {
        items(casts) {
            Column(
                verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.extraSmall),
                modifier = Modifier.width(100.dp)
            ) {
                ImageNetwork(
                    imagePath = it.profilePath ?: "",
                    contentDescription = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(MaterialTheme.shapes.small)
                )
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = it.character ?: it.job ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}