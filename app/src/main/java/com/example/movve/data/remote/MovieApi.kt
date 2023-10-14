package com.example.movve.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("popular")
    suspend fun getAllPopularMovies(
        @Query("page") page: Int
    ): MovieResult

    @GET("now_playing")
    suspend fun getAllNowPlayingMovies(
        @Query("page") page: Int
    ): MovieResult

    @GET("search")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("q") q: String
    ): MovieResult

}