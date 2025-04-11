package com.amirnlz.core.data.movie.mapper

import com.amirnlz.core.domain.movie.model.MovieCast
import com.amirnlz.core.domain.movie.model.MovieCredits
import com.amirnlz.core.network.dto.movie.MovieCastDto
import com.amirnlz.core.network.dto.movie.MovieCreditsDto

fun MovieCreditsDto.mapToMovieCredits(): MovieCredits = MovieCredits(
  id = id,
  cast = cast.map { it.mapToMovieCast() },
  crew = crew.map { it.mapToMovieCast() },
)

fun MovieCastDto.mapToMovieCast(): MovieCast = MovieCast(
  adult = adult,
  gender = gender,
  id = id,
  knownForDepartment = knownForDepartment,
  name = name,
  originalName = originalName,
  popularity = popularity,
  profilePath = profilePath,
  castID = castID,
  character = character,
  creditID = creditID,
  order = order,
  department = department,
  job = job,
)
