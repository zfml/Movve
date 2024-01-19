package com.example.movve.data.remote

data class MovieDto(
    val id: String,
    val title: String,
    val release_date: String,
    val poster_path: String? = null
)
