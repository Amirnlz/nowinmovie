package com.amirnlz.core.network.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: Any? = null,
    val budget: Long,
    val genres: List<GenreDto>,
    val homepage: String,
    val id: Long,
    val imdbID: String?,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompanyDto>?,
    val productionCountries: List<ProductionCountryDto>?,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    val spokenLanguages: List<SpokenLanguageDto>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long
)

data class GenreDto(
    val id: Long,
    val name: String
)

data class ProductionCompanyDto(
    val id: Long,
    val logoPath: String,
    val name: String,
    val originCountry: String
)

data class ProductionCountryDto(
    @SerializedName("iso_3166_1") val iso3166_1: String?,
    val name: String
)

data class SpokenLanguageDto(
    val englishName: String,
    @SerializedName("iso_639_1") val iso639_1: String?,
    val name: String
)
