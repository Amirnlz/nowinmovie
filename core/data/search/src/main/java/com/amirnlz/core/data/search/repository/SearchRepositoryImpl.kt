package com.amirnlz.core.data.search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amirnlz.core.data.search.data_source.remote.SearchPagingSource
import com.amirnlz.core.data.search.data_source.remote.SearchRemoteDataSource
import com.amirnlz.core.domain.search.model.Movie
import com.amirnlz.core.domain.search.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val DEFAULT_PAGE_SIZE = 20

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource
) : SearchRepository {
    override suspend fun getSearchMovie(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchPagingSource { page -> remoteDataSource.searchMovie(query, page) }
            }
        ).flow
    }
}