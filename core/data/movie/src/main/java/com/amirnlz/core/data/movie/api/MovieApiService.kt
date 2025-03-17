package com.amirnlz.core.data.movie.api

import com.amirnlz.core.data.movie.dto.MovieDetailsDto
import com.amirnlz.core.data.movie.dto.MovieListDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieListDto

    @GET("/3/movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String = "en-US",
    ): MovieDetailsDto

    @GET("/3/account/{account_id}/favorite/movies")
    fun getFavoriteMovies(
        @Path("account_id") accountId: Long
    ): MovieListDto

    @POST("/3/account/{account_id}/favorite")
    fun addFavoriteMovie(@Path("account_id") accountId: Long, movieId: Long): Unit
}