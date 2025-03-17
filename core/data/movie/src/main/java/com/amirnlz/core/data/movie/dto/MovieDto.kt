package com.amirnlz.core.data.movie.dto

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
