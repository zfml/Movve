package com.example.movve.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.movve.data.mappers.toMovie
import com.example.movve.domain.model.Movie
import com.example.movve.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {


    fun getPopularMovies(): Flow<PagingData<Movie>> = movieRepository.getAllPopularMovie().map { it -> it.map { it.toMovie() } }.cachedIn(viewModelScope)

    fun getNowPlayingMovies(): Flow<PagingData<Movie>> = movieRepository.getAllNowPlayingMovies().map { it -> it.map { it.toMovie() } }.cachedIn(viewModelScope)

}

