package com.amirnlz.core.network.api

import com.amirnlz.core.network.Authenticated
import com.amirnlz.core.network.dto.movie.MovieCreditsDto
import com.amirnlz.core.network.dto.movie.MovieDetailsDto
import com.amirnlz.core.network.dto.movie.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("/3/movie/popular")
    @Authenticated
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): MovieListDto

    @GET("/3/movie/now_playing")
    @Authenticated
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
    ): MovieListDto

    @GET("/3/movie/top_rated")
    @Authenticated
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): MovieListDto

    @GET("/3/movie/upcoming")
    @Authenticated
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): MovieListDto

    @GET("/3/movie/{movie_id}")
    @Authenticated
    suspend fun getMovieDetails(
        @Path("movie_id")
        movieId: Long,
    ): MovieDetailsDto

    @GET("/3/movie/{movie_id}/credits")
    @Authenticated
    suspend fun getMovieCredits(
        @Path("movie_id")
        movieId: Long,
    ): MovieCreditsDto

    @GET("/3/search/movie")
    @Authenticated
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): MovieListDto

    @GET("/3/movie/{movie_id}/recommendations")
    @Authenticated
    suspend fun getMovieRecommendations(
        @Path("movie_id")
        movieId: Long,
//        @Query("page") page: Int, // Uncomment if pagination is needed
    ): MovieListDto
}
