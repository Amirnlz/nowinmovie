package com.amirnlz.core.domain.search.model

import java.time.LocalDate

data class Movie(
    val backdropPath: String? = null,
    val genreIDS: List<Long>?,
    val id: Long,
    val originalTitle: String,
    val overview: String,
    val posterPath: String? = null,
    val releaseDate: LocalDate? = null,
    val title: String? = null,
    val voteAverage: Double? = null,
)
