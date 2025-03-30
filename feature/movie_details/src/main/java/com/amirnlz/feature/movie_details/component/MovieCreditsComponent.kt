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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.amirnlz.core.domain.movie.model.MovieCast
import com.amirnlz.core.domain.movie.model.MovieCredits
import com.amirnlz.core.ui.ImageNetwork

@Composable
internal fun MovieCreditsComponent(modifier: Modifier = Modifier, movieCredits: MovieCredits) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val list = listOf("Cast", "Director & crew")

    Column(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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
            .clip(RoundedCornerShape(10.dp)),
        indicator = { Box {} }
    ) {
        list.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex
            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) colorScheme.secondaryContainer
                else colorScheme.background,
                animationSpec = tween(durationMillis = 300)
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) colorScheme.onSecondaryContainer
                else colorScheme.onSurface,
                animationSpec = tween(durationMillis = 300)
            )

            Tab(
                selected = isSelected,
                onClick = { onClick(index) },
                modifier = Modifier
                    .padding(6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(backgroundColor)
                    .padding(vertical = 10.dp, horizontal = 8.dp),
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
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(casts) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.width(100.dp)
            ) {
                ImageNetwork(
                    imagePath = it.profilePath ?: "",
                    contentDescription = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(8.dp))
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