package com.example.movve.domain.repository


import androidx.paging.PagingData
import com.example.movve.data.remote.MovieDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getAllPopularMovie(): Flow<PagingData<MovieDto>>
    fun getAllNowPlayingMovies(): Flow<PagingData<MovieDto>>
    suspend fun getMovieById(id: String): MovieDto
    fun searchMovies(query: String): Flow<PagingData<MovieDto>>

}