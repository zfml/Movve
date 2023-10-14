package com.example.movve.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movve.data.paging.NowPlayingMoviePagingSource
import com.example.movve.data.paging.PopularMoviePagingSource
import com.example.movve.data.remote.MovieApi
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

}