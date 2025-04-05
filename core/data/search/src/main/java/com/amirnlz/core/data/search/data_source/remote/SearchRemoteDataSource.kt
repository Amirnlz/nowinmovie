package com.amirnlz.core.data.search.data_source.remote

import com.amirnlz.core.network.api.SearchApiService
import com.amirnlz.core.network.dto.movie.MovieListDto
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(private val searchApi: SearchApiService) {
    suspend fun searchMovie(query: String, page: Int): MovieListDto {
        try {
            return searchApi.searchMovies(query = query, page = page)
        } catch (e: Exception) {
            throw e
        }
    }
}