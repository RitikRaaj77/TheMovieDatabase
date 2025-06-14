package com.example.themoviedatabase.data.repository

import com.example.themoviedatabase.data.model.Movie
import com.example.themoviedatabase.data.remote.ApiService

class MovieRepository(private val apiService: ApiService) {
    suspend fun getPopularMovies(apiKey: String, language: String = "en-US", page: Int = 1): List<Movie> {
        return apiService.getPopularMovies(apiKey, language, page).results
    }
}