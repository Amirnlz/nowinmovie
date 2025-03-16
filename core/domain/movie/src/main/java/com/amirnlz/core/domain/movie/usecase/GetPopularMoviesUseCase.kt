package com.amirnlz.core.domain.movie.usecase

import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.repository.MovieRepository

class GetPopularMoviesUseCase(private val repository: MovieRepository) {

    suspend operator fun invoke(): Result<List<Movie>> {
        return repository.getPopularMovies()
    }
}