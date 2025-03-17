package com.amirnlz.core.domain.movie.usecase

import com.amirnlz.core.domain.movie.repository.MovieRepository

class ToggleFavoriteMovieUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Long): Result<Unit> {
        return repository.toggleFavoriteMovie(movieId)
    }
}