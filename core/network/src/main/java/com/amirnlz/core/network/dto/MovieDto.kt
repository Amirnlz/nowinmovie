package com.amirnlz.core.network.dto

data class MovieListDto(
    val page: Long,
    val results: List<MovieDto>,
    val totalPages: Long,
    val totalResults: Long
)

data class MovieDto(
    val adult: Boolean,
    val backdropPath: String? = null,
    val genreIDS: List<Long>,
    val id: Long,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String? = null,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long
)
