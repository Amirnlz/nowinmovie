package com.amirnlz.feature.movie_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amirnlz.core.domain.movie.model.Genre
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.model.toMovieTime
import com.amirnlz.core.ui.ImageNetwork
import com.amirnlz.feature.movie_detail.R

@Composable
internal fun MovieDetailsComponent(modifier: Modifier = Modifier, movieDetails: MovieDetails) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(Color.LightGray.copy(alpha = 0.6f))
    ) {

        ImageNetwork(
            imagePath = movieDetails.backdropPath,
            contentDescription = stringResource(
                R.string.backdrop_image_description,
                movieDetails.title
            ),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                movieDetails.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    movieDetails.releaseDate.year.toString(),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    movieDetails.runtime.toMovieTime(),
                    fontWeight = FontWeight.Medium
                )
                RatingComponent(rating = movieDetails.voteAverage)
            }
            GenresItem(genres = movieDetails.genres)
            Text(
                "Overview",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                movieDetails.overview,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun GenresItem(genres: List<Genre>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres) {
            Text(
                it.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                    .padding(vertical = 8.dp, horizontal = 12.dp)

            )
        }
    }
}

@Composable
private fun RatingComponent(rating: Double) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            Icons.Filled.Star,
            contentDescription = "Rating Star",
            tint = Color.Yellow
        )
        Text(
            String.format("%.1f", rating),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
