package com.amirnlz.core.data.movie.mapper

import com.amirnlz.core.domain.movie.model.Genre
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.model.MovieList
import com.amirnlz.core.domain.movie.model.ProductionCompany
import com.amirnlz.core.domain.movie.model.ProductionCountry
import com.amirnlz.core.domain.movie.model.SpokenLanguage
import com.amirnlz.core.network.dto.GenreDto
import com.amirnlz.core.network.dto.MovieDetailsDto
import com.amirnlz.core.network.dto.MovieDto
import com.amirnlz.core.network.dto.MovieListDto
import com.amirnlz.core.network.dto.ProductionCompanyDto
import com.amirnlz.core.network.dto.ProductionCountryDto
import com.amirnlz.core.network.dto.SpokenLanguageDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun MovieListDto.mapToMovieList(): MovieList {
    return MovieList(
        page,
        totalResults = totalResults,
        totalPages = totalPages,
        results = results.map { it.mapToMovie() }
    )

}

fun MovieDto.mapToMovie(): Movie {
    return Movie(
        adult,
        backdropPath,
        genreIDS,
        id,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate?.toLocalDate(),
        title,
        video,
        voteAverage,
        voteCount,
    )
}

fun MovieDetailsDto.mapToMovieDetails(): MovieDetails {
    return MovieDetails(
        adult,
        backdropPath,
        belongsToCollection,
        budget,
        genres.map { it.mapToGenres() },
        homepage,
        id,
        imdbID,
        originCountry,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        productionCompanies?.map { it.mapToProductionCompanies() },
        productionCountries?.map { it.mapToProductionCountries() },
        releaseDate?.toLocalDate() ?: LocalDate.now(),
        revenue,
        runtime,
        spokenLanguages.map { it.mapToSpokenLanguage() },
        status,
        tagline,
        title,
        video,
        voteAverage,
        voteCount,
    )
}

fun GenreDto.mapToGenres(): Genre {
    return Genre(
        id, name
    )
}

fun ProductionCompanyDto.mapToProductionCompanies(): ProductionCompany {
    return ProductionCompany(
        id = id,
        logoPath = logoPath,
        name = name,
        originCountry = originCountry
    )
}

fun ProductionCountryDto.mapToProductionCountries(): ProductionCountry {
    return ProductionCountry(iso3166_1, name)
}

fun SpokenLanguageDto.mapToSpokenLanguage(): SpokenLanguage {
    return SpokenLanguage(
        englishName, iso639_1, name
    )
}

private fun String.toLocalDate(pattern: String = "yyyy-MM-dd"): LocalDate? {
    return try {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        LocalDate.parse(this, formatter)
    } catch (e: Exception) {
        println("Error parsing date string: $e")
        null
    }
}