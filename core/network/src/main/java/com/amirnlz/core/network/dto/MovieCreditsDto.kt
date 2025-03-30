package com.amirnlz.core.network.dto

data class MovieCreditsDto(
    val id: Long,
    val cast: List<MovieCastDto>,
    val crew: List<MovieCastDto>
)

data class MovieCastDto(
    val adult: Boolean,
    val gender: Long,
    val id: Long,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String? = null,
    val castID: Long? = null,
    val character: String? = null,
    val creditID: String? = null,
    val order: Long? = null,
    val department: String? = null,
    val job: String? = null
)
