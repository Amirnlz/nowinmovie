package com.amirnlz.core.data.search.mapper

import com.amirnlz.core.domain.search.model.Movie
import com.amirnlz.core.network.dto.movie.MovieDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun MovieDto.mapToMovie(): Movie {
    return Movie(
        backdropPath = backdropPath,
        genreIDS = genreIDS,
        id = id,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate?.toLocalDate(),
        title = title,
        voteAverage = voteAverage
    )
}

private fun String.toLocalDate(pattern: String = "yyyy-MM-dd"): LocalDate? {
    return try {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        LocalDate.parse(this, formatter)
    } catch (e: Exception) {
        println("Error parsing date string: $e")
        null
    }
}