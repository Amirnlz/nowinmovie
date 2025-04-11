package com.amirnlz.core.domain.movie.model

data class MovieCredits(
  val id: Long,
  val cast: List<MovieCast>,
  val crew: List<MovieCast>,
)

data class MovieCast(
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
  val job: String? = null,
)
