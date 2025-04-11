package com.amirnlz.core.domain.movie.usecase

import com.amirnlz.core.domain.movie.model.MovieDetails
import com.amirnlz.core.domain.movie.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: MovieRepository) {

  suspend operator fun invoke(movieId: Long): Result<MovieDetails> = repository.getMovieDetails(movieId)
}
