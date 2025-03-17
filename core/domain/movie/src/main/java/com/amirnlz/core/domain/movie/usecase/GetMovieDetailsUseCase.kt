package com.amirnlz.core.domain.movie.usecase

import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.repository.MovieRepository

class GetMovieDetailsUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Long): Result<MovieDetails> {
        return repository.getMovieDetails(movieId)
    }
}