package com.example.movve.presentation.movieDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movve.data.mappers.toMovie
import com.example.movve.data.remote.MovieDto
import com.example.movve.data.repository.MovieRepositoryImpl
import com.example.movve.domain.model.Movie
import com.example.movve.domain.repository.MovieRepository
import com.example.movve.presentation.util.Constants.MOVIE_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    saveStateHandler: SavedStateHandle
): ViewModel(){
    private val movieId: String = checkNotNull(saveStateHandler[MOVIE_ID_KEY])

    private val _state = mutableStateOf(Movie())

    val state = _state

    init {
        viewModelScope.launch {
            _state.value = movieRepository.getMovieById(movieId).toMovie()

        }
    }

}


//fun MovieDto.toMovieDetailUiState() =
//    MovieDetailUiState(
//        movie = Movie(
//            id = id,
//            title = title,
//            releaseDate = release_date,
//            posterPath = poster_path
//        )
//    )
//data class MovieDetailUiState(
//    val movie: Movie = Movie()
//)
//
//
//data class Movie(
//    val id: String = "",
//    val title: String = "",
//    val releaseDate: String = "",
//    val posterPath: String = ""
//)