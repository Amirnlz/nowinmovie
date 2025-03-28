package com.amirnlz.core.network.api

import com.amirnlz.core.network.Authenticated
import com.amirnlz.core.network.dto.MovieDetailsDto
import com.amirnlz.core.network.dto.MovieListDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("/3/movie/popular")
    @Authenticated
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MovieListDto

    @GET("/3/movie/now_playing")
    @Authenticated
    suspend fun getTrendingMovies(
        @Query("page") page: Int
    ): MovieListDto

    @GET("/3/movie/top_rated")
    @Authenticated
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): MovieListDto

    @GET("/3/movie/upcoming")
    @Authenticated
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): MovieListDto

    @GET("/3/account/{account_id}/favorite/movies")
    @Authenticated
    suspend fun getFavoriteMovies(
        @Path("account_id") accountId: Long,
        @Query("page") page: Int
    ): MovieListDto

    @GET("/3/movie/{movie_id}")
    @Authenticated
    suspend fun getMovieDetails(
        @Path("movie_id")
        movieId: Long,
    ): MovieDetailsDto

    @POST("/3/account/{account_id}/favorite")
    @Authenticated
    suspend fun addFavoriteMovie(
        @Path("account_id") accountId: Long,
        movieId: Long
    ): Unit
}