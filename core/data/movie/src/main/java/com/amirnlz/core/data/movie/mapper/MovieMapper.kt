package com.amirnlz.core.data.movie.mapper

import com.amirnlz.core.database.entity.MovieEntity
import com.amirnlz.core.domain.movie.model.Genre
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.model.ProductionCompany
import com.amirnlz.core.domain.movie.model.ProductionCountry
import com.amirnlz.core.domain.movie.model.SpokenLanguage
import com.amirnlz.core.network.dto.movie.GenreDto
import com.amirnlz.core.network.dto.movie.MovieDetailsDto
import com.amirnlz.core.network.dto.movie.MovieDto
import com.amirnlz.core.network.dto.movie.ProductionCompanyDto
import com.amirnlz.core.network.dto.movie.ProductionCountryDto
import com.amirnlz.core.network.dto.movie.SpokenLanguageDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun MovieDto.mapToMovie(): Movie {
  return Movie(
    backdropPath = backdropPath,
    genreIDS = genreIDS,
    id = id,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate?.toLocalDate(),
    title = title,
    voteAverage = voteAverage,
  )
}

fun MovieEntity.mapToMovie(): Movie {
  return Movie(
    id = id,
    title = title,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    genreIDS = null,
    backdropPath = "",
    originalTitle = "",
    overview = "",
  )
}

fun MovieDetailsDto.mapToMovieDetails(): MovieDetails {
  return MovieDetails(
    backdropPath = backdropPath,
    genres = genres.map { it.mapToGenres() },
    id = id,
    imdbID = imdbID,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    productionCompanies = productionCompanies?.map { it.mapToProductionCompanies() },
    productionCountries = productionCountries?.map { it.mapToProductionCountries() },
    releaseDate = releaseDate.toLocalDate() ?: LocalDate.now(),
    runtime = runtime,
    spokenLanguages = spokenLanguages.map { it.mapToSpokenLanguage() },
    title = title,
    voteAverage = voteAverage,
  )
}

fun MovieDetails.mapToMovieEntity(): MovieEntity {
  return MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
  )
}

fun GenreDto.mapToGenres(): Genre {
  return Genre(
    id,
    name,
  )
}

fun ProductionCompanyDto.mapToProductionCompanies(): ProductionCompany {
  return ProductionCompany(
    id = id,
    logoPath = logoPath,
    name = name,
    originCountry = originCountry,
  )
}

fun ProductionCountryDto.mapToProductionCountries(): ProductionCountry {
  return ProductionCountry(iso3166_1, name)
}

fun SpokenLanguageDto.mapToSpokenLanguage(): SpokenLanguage {
  return SpokenLanguage(
    englishName,
    iso639_1,
    name,
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
