package com.example.movve.presentation.movieDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movve.data.remote.MovieApi
import com.example.movve.data.remote.MovieDto
import com.example.movve.data.repository.MovieRepository
import com.example.movve.presentation.util.Constants.MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    saveStateHandler: SavedStateHandle
): ViewModel(){
    private val movieId: String = checkNotNull(saveStateHandler[MOVIE_ID])

    private val _state = mutableStateOf(MovieDetailUiState())

    val state = _state

    init {
        viewModelScope.launch {
            _state.value = movieRepository.getMovieById(movieId).toMovieDetailUiState()

        }
    }

}


fun MovieDto.toMovieDetailUiState() =
    MovieDetailUiState(
        movie = Movie(
            id = id,
            title = title,
            releaseDate = release_date,
            posterPath = poster_path
        )
    )
data class MovieDetailUiState(
    val movie: Movie = Movie()
)


data class Movie(
    val id: String = "",
    val title: String = "",
    val releaseDate: String = "",
    val posterPath: String = ""
)