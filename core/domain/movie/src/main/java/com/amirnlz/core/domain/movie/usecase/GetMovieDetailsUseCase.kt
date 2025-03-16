package com.amirnlz.core.domain.movie.usecase

import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.repository.MovieRepository

class GetMovieDetailsUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): Result<Movie> {
        return repository.getMovieDetails(movieId)
    }
}