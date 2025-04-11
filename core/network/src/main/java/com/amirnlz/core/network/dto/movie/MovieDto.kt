package com.amirnlz.core.network.dto.movie

data class MovieListDto(val page: Long, val results: List<MovieDto>, val totalPages: Long, val totalResults: Long)

data class MovieDto(val adult: Boolean, val backdropPath: String? = null, val genreIDS: List<Long>?, val id: Long, val originalLanguage: String, val originalTitle: String, val overview: String, val popularity: Double? = null, val posterPath: String? = null, val releaseDate: String? = null, val title: String? = null, val video: Boolean? = null, val voteAverage: Double? = null, val voteCount: Long? = null)
