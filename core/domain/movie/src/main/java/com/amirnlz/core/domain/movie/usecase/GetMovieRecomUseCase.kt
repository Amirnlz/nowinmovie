package com.amirnlz.core.domain.movie.usecase

import com.amirnlz.core.domain.movie.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMovieRecomUseCase @Inject constructor(private val movieRepository: MovieRepository) {
  suspend operator fun invoke(movieId: Long) = movieRepository.getMovieRecommendations(movieId)
}
