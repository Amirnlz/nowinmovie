package com.amirnlz.core.domain.movie.model

import java.time.LocalDate

data class Movie(
    val adult: Boolean,
    val backdropPath: String? = null,
    val genreIDS: List<Long>?,
    val id: Long,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: LocalDate? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Long? = null
)
