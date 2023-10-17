package com.example.movve.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movve.data.paging.NowPlayingMoviePagingSource
import com.example.movve.data.paging.PopularMoviePagingSource
import com.example.movve.data.paging.SearchMoviePagingSource
import com.example.movve.data.remote.MovieApi
import com.example.movve.data.remote.MovieDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) {

    fun getAllPopularMovie() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {PopularMoviePagingSource(movieApi)}
    ).flow

    fun getAllNowPlayingMovies() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {NowPlayingMoviePagingSource(movieApi)}
    ).flow


    suspend fun getMovieById(id: String): MovieDto {
        return withContext(Dispatchers.IO) {
            movieApi.getImageById(id)

        }
    }

    fun searchMovies(query: String) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {SearchMoviePagingSource(movieApi,query)}
    ).flow


}