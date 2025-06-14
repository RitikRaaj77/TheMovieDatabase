package com.example.themoviedatabase.data.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Float,
    val popularity: Float
)