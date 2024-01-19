package com.example.movve.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movve.data.paging.NowPlayingMoviePagingSource
import com.example.movve.data.paging.PopularMoviePagingSource
import com.example.movve.data.paging.SearchMoviePagingSource
import com.example.movve.data.remote.MovieApi
import com.example.movve.data.remote.MovieDto
import com.example.movve.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl(
    private val movieApi: MovieApi
): MovieRepository{


    override fun getAllPopularMovie(): Flow<PagingData<MovieDto>> {
       return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {PopularMoviePagingSource(movieApi)}
        ).flow
    }

    override fun getAllNowPlayingMovies(): Flow<PagingData<MovieDto>> {
       return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {NowPlayingMoviePagingSource(movieApi)}
        ).flow
    }

    override suspend fun getMovieById(id: String): MovieDto {
        return withContext(Dispatchers.IO) {
            movieApi.getImageById(id)
        }
    }

    override fun searchMovies(query: String): Flow<PagingData<MovieDto>> {
       return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {SearchMoviePagingSource(movieApi,query)}
        ).flow
    }







}