package com.amirnlz.core.domain.search.model

data class MovieList(
    val page: Long,
    val results: List<Movie>,
    val totalPages: Long,
    val totalResults: Long
)
