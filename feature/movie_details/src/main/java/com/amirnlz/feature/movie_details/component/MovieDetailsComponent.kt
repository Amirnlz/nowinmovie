package com.amirnlz.feature.movie_details.component

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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.amirnlz.core.designsystem.theme.NowinmovieTheme
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.model.ProductionCompany
import com.amirnlz.core.domain.movie.model.toMovieTime
import com.amirnlz.core.ui.ImageNetwork
import com.amirnlz.feature.movie_detail.R

@Composable
internal fun MovieDetailsComponent(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails,
) {
    Column(modifier = modifier) {
        MovieBackdropImage(movieDetails.backdropPath)
        MovieContentSection(movieDetails)
    }
}

@Composable
private fun MovieBackdropImage(backdropPath: String) {
    ImageNetwork(
        imagePath = backdropPath,
        contentDescription = stringResource(R.string.backdrop_image_description),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(NowinmovieTheme.spacing.small)
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
private fun MovieContentSection(movieDetails: MovieDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(NowinmovieTheme.dimens.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.medium),
    ) {
        MovieHeaderSection(movieDetails)
        ChipList(
            movieDetails.genres.map { it.name },
            MaterialTheme.colorScheme.secondaryContainer
        )
        MovieDetailsSections(movieDetails)
    }
}

@Composable
private fun MovieHeaderSection(movieDetails: MovieDetails) {
    Column(verticalArrangement = Arrangement.spacedBy(NowinmovieTheme.spacing.small)) {
        Text(
            text = movieDetails.title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
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