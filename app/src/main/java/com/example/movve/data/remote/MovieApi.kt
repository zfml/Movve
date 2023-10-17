package com.example.movve.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getAllPopularMovies(
        @Query("page") page: Int
    ): MovieResult

    @GET("movie/now_playing")
    suspend fun getAllNowPlayingMovies(
        @Query("page") page: Int
    ): MovieResult

    @GET("movie/{movie_id}")
    suspend fun getImageById(
        @Path("movie_id") id: String
    ): MovieDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("query") query: String
    ): MovieResult

}