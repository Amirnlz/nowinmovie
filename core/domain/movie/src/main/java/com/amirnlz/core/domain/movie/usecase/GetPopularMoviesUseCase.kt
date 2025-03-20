package com.amirnlz.core.domain.movie.usecase

import com.amirnlz.core.domain.movie.model.MovieList
import com.amirnlz.core.domain.movie.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(): Result<MovieList> {
        return repository.getPopularMovies()
    }
}