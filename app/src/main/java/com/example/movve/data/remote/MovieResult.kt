package com.example.movve.data.remote

import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("results")
    val results: List<MovieDto>
)
