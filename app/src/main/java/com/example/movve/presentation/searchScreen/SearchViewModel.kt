package com.example.movve.presentation.searchScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.movve.data.mappers.toMovie
import com.example.movve.data.remote.MovieDto
import com.example.movve.data.repository.MovieRepositoryImpl
import com.example.movve.domain.model.Movie
import com.example.movve.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel(){

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val searchMovies = _searchMovies

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            movieRepository.searchMovies(query).cachedIn(viewModelScope).collect{ it ->
                _searchMovies.value = it.map { it.toMovie() }
            }
        }
    }


}