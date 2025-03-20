package com.amirnlz.core.domain.movie.model


data class MovieDetails(
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: Any? = null,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,
    val imdbID: Any? = null,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<Any?>,
    val productionCountries: List<Any?>,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Long,
    val voteCount: Long
)


data class Genre(
    val id: Long,
    val name: String
)

data class SpokenLanguage(
    val englishName: String,
    val iso639_1: String,
    val name: String
)

