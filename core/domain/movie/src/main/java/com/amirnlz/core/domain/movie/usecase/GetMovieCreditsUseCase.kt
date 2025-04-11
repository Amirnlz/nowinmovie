package com.amirnlz.core.domain.movie.usecase

import com.amirnlz.core.domain.movie.model.MovieCredits
import com.amirnlz.core.domain.movie.repository.MovieRepository
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(private val repository: MovieRepository) {

  suspend operator fun invoke(movieId: Long): Result<MovieCredits> {
    return repository.getMovieCredits(movieId)
  }
}
