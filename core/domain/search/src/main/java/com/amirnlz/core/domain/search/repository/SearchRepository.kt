package com.amirnlz.core.domain.search.repository

import androidx.paging.PagingData
import com.amirnlz.core.domain.search.model.Movie
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchMovie(query : String): Flow<PagingData<Movie>>
}