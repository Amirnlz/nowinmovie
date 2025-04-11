package com.amirnlz.feature.movie_details.component

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.amirnlz.core.designsystem.component.ImageNetwork
import com.amirnlz.core.designsystem.theme.NowinmovieTheme
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.model.ProductionCompany
import com.amirnlz.core.domain.movie.model.toMovieTime
import com.amirnlz.feature.movie_detail.R

@Composable
internal fun MovieDetailsComponent(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails?,
    isMovieFavorite: Boolean,
    onMovieFavoriteChanged: (MovieDetails) -> Unit
) {
    if (movieDetails == null) return
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.small)
    ) {
        MovieBackdropImage(movieDetails.backdropPath)
        MovieContentSection(movieDetails, isMovieFavorite, onMovieFavoriteChanged)

    }
}

@Composable
private fun MovieBackdropImage(backdropPath: String) {
    ImageNetwork(
        imagePath = backdropPath,
        contentDescription = stringResource(R.string.backdrop_image_description),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .fillMaxWidth()
            .aspectRatio(NowinmovieTheme.dimens.backdropAspectRatio)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.large
            )
    )
}

@Composable
private fun MovieContentSection(
    movieDetails: MovieDetails,
    isMovieFavorite: Boolean,
    onMovieFavoriteChanged: (MovieDetails) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.medium),
    ) {
        MovieHeaderSection(movieDetails, isMovieFavorite, onMovieFavoriteChanged)
        ChipList(
            movieDetails.genres.map { it.name },
            MaterialTheme.colorScheme.secondaryContainer
        )
        MovieDetailsSections(movieDetails)
    }
}

@Composable
private fun MovieHeaderSection(
    movieDetails: MovieDetails,
    isMovieFavorite: Boolean,
    onMovieFavoriteChanged: (MovieDetails) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.small)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movieDetails.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            FavoriteButton(
                isChecked = isMovieFavorite,
                onClick = { onMovieFavoriteChanged(movieDetails) }
            )
        }

        if (movieDetails.originalTitle != movieDetails.title) {
            Text(
                movieDetails.originalTitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        MovieAttributesRow(
            releaseYear = movieDetails.releaseDate.year,
            runtime = movieDetails.runtime,
            rating = movieDetails.voteAverage
        )
    }
}

@Composable
private fun MovieAttributesRow(releaseYear: Int, runtime: Long, rating: Double) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ChipList(
            listOf(releaseYear.toString(), runtime.toMovieTime())
        )
        RatingChip(rating = rating)
    }
}

@Composable
private fun MovieDetailsSections(movieDetails: MovieDetails) {
    Column(verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.medium)) {
        OverviewSection(movieDetails.overview)
        movieDetails.productionCompanies?.let { companies ->
            CompanyLogoList(
                companies = companies,
                title = stringResource(R.string.production_companies)
            )
        }
        movieDetails.productionCountries?.let { countries ->
            SectionTitle(stringResource(R.string.production_countries))
            ChipList(items = countries.map { it.name })
        }

        movieDetails.spokenLanguages.takeIf { it.isNotEmpty() }?.let { languages ->
            SectionTitle(stringResource(R.string.production_languages))
            ChipList(items = languages.map { it.englishName })
        }
    }
}

@Composable
private fun OverviewSection(overview: String) {
    Column(verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.extraSmall)) {
        SectionTitle(stringResource(R.string.overview))
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}


@Composable
private fun RatingChip(rating: Double) {
    Surface(
        color = MaterialTheme.colorScheme.tertiaryContainer,
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(
                NowinmovieTheme.dimens.screenPadding
            ),
            horizontalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.extraSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(R.string.rating_star),
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = String.format(java.util.Locale.getDefault(), "%.1f", rating),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun CompanyLogoList(companies: List<ProductionCompany>, title: String) {
    Column(verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.extraSmall)) {
        SectionTitle(title)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(companies) { company ->
                ProductionCompanyChip(company = company)
            }
        }
    }
}

@Composable
private fun ProductionCompanyChip(company: ProductionCompany) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(NowinmovieTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.extraSmall)
        ) {
            company.logoPath
            ImageNetwork(
                imagePath = company.logoPath ?: "",
                contentDescription = "Logo of ${company.name}",
                modifier = Modifier.size(NowinmovieTheme.dimens.iconSizeLarge)
            )

            Text(
                text = company.name,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
        ),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = NowinmovieTheme.spacing.extraSmall)
    )
}

@Composable
private fun ChipList(
    items: List<String>,
    chipColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.small)) {
        items(items) { item ->
            Surface(
                color = chipColor,
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(
                        NowinmovieTheme.dimens.screenPadding
                    )
                )
            }
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun FavoriteButton(
    isChecked: Boolean,
    onClick: () -> Unit
) {
    IconToggleButton(
        checked = isChecked,
        onCheckedChange = { onClick() }
    ) {
        val transition = updateTransition(isChecked, label = "Checked indicator")

        val tint by transition.animateColor(
            label = "Tint"
        ) { isChecked ->
            if (isChecked) Color.Red else Color.Black
        }

        val size by transition.animateDp(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    keyframes {
                        durationMillis = 250
                        30.dp at 0 using LinearOutSlowInEasing // for 0-15 ms
                        35.dp at 15 using FastOutLinearInEasing // for 15-75 ms
                        40.dp at 75 // ms
                        35.dp at 150 // ms
                    }
                } else {
                    spring(stiffness = Spring.StiffnessVeryLow)
                }
            },
            label = "Size"
        ) { 30.dp }

        Icon(
            imageVector = if (isChecked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}
