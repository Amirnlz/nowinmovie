package com.amirnlz.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.model.MovieList
import com.amirnlz.core.ui.ImageNetwork

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    onMovieClicked: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.popularMovies.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        onMovieClicked = onMovieClicked,
        uiState = uiState
    )

}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onMovieClicked: (Long) -> Unit,
    uiState: MovieListUiState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (uiState) {
            is MovieListUiState.Data -> MovieListItems(
                movieList = uiState.movies,
                onMovieClicked = onMovieClicked
            )

            is MovieListUiState.Error ->
                Text(uiState.error.message ?: "An Error message")

            MovieListUiState.Loading -> CircularProgressIndicator()
        }
    }
}

@Composable
private fun MovieListItems(
    modifier: Modifier = Modifier,
    movieList: MovieList,
    onMovieClicked: (Long) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(movieList.results) { movie ->
            MovieItem(movie = movie, onMovieClicked = onMovieClicked)
        }
    }
}

@Composable
private fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClicked: (Long) -> Unit
) {
    if (movie.posterPath != null) {
        Card(
            modifier = modifier
                .width(160.dp)
                .clickable { onMovieClicked(movie.id) },
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.TopEnd
                ) {
                    ImageNetwork(
                        imagePath = movie.posterPath!!,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .height(240.dp)
                            .fillMaxWidth(),
                    )
                    // Rating Badge
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = "Rating Star",
                                tint = Color.White
                            )
                            Text(
                                text = String.format("%.1f", movie.voteAverage),
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                // Movie Title
                Text(
                    text = movie.title ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}