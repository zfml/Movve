package com.example.movve.data.mappers

import com.example.movve.data.remote.MovieDto
import com.example.movve.domain.model.Movie

fun MovieDto.toMovie(): Movie = Movie(
    id = id,
    title = title,
    releaseDate = release_date,
    posterPath = poster_path ?: ""
)