package com.amirnlz.core.data.movie.dto

data class MovieListDto(
    val page: Long,
    val results: List<MovieDto>,
    val totalPages: Long,
    val totalResults: Long
)
