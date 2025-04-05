package com.amirnlz.core.domain.search.usecase

import androidx.paging.PagingData
import com.amirnlz.core.domain.search.model.Movie
import com.amirnlz.core.domain.search.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSearchMovieUseCase @Inject constructor(private val repository: SearchRepository) {

    suspend operator fun invoke(query: String): Flow<PagingData<Movie>> {
        return repository.getSearchMovie(query)
    }
}