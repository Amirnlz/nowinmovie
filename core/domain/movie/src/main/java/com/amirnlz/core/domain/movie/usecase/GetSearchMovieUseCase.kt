package com.amirnlz.core.domain.movie.usecase

import androidx.paging.PagingData
import com.amirnlz.core.domain.movie.model.Movie
import com.amirnlz.core.domain.movie.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSearchMovieUseCase @Inject constructor(private val repository: MovieRepository) {

  suspend operator fun invoke(query: String): Flow<PagingData<Movie>> = repository.getSearchMovie(query)
}
