package com.amirnlz.core.network.api

import com.amirnlz.core.network.Authenticated
import com.amirnlz.core.network.dto.movie.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("/3/search/movie")
    @Authenticated
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): MovieListDto

//    @GET("/3/search/person")
//    @Authenticated
//    suspend fun searchPeople(
//        @Query("query") query: String,
//        @Query("page") page: Int,
//    ): MovieListDto
}