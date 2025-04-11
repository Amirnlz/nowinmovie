package com.amirnlz.core.domain.movie.usecase

import com.amirnlz.core.domain.movie.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieFavoriteStateUseCase @Inject constructor(private val repository: MovieRepository) {

  suspend operator fun invoke(movieId: Long): Flow<Boolean> {
    return repository.getMovieFavoriteState(movieId)
  }
}
