package com.example.movve.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movve.data.remote.MovieDto
import com.example.movve.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val  movieRepository: MovieRepository
) : ViewModel() {

    fun getPopularMovies(): Flow<PagingData<MovieDto>> = movieRepository.getAllPopularMovie().cachedIn(viewModelScope)

    fun getNowPlayingMovies(): Flow<PagingData<MovieDto>> = movieRepository.getAllNowPlayingMovies().cachedIn(viewModelScope)

}