package com.amirnlz.core.domain.movie.model

import java.time.LocalDate

data class MovieDetails(val backdropPath: String, val genres: List<Genre>, val id: Long, val imdbID: String?, val originCountry: List<String>, val originalLanguage: String, val originalTitle: String, val overview: String, val posterPath: String, val productionCompanies: List<ProductionCompany>?, val productionCountries: List<ProductionCountry>?, val releaseDate: LocalDate, val runtime: Long, val spokenLanguages: List<SpokenLanguage>, val title: String, val voteAverage: Double)

data class Genre(val id: Long, val name: String)

data class ProductionCompany(val id: Long, val logoPath: String?, val name: String, val originCountry: String)

data class ProductionCountry(val iso3166_1: String?, val name: String)

data class SpokenLanguage(val englishName: String, val iso639_1: String?, val name: String)

fun Long.toMovieTime(): String {
  val hours = this.floorDiv(60)
  val minutes = this % 60
  return "${hours}h ${minutes}m"
}
