package com.example.themoviedatabase.userInterface.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedatabase.data.model.Movie
import com.example.themoviedatabase.data.remote.NetworkModule
import com.example.themoviedatabase.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val repository = MovieRepository(NetworkModule.provideApiService())

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            try {
                val apiKey = "38a73d59546aa378980a88b645f487fc"
                val movies = repository.getPopularMovies(apiKey)
                _movies.value = movies
                Log.d("MovieListViewModel", "Fetched ${movies.size} movies")
            } catch (e: Exception) {
                Log.e("MovieListViewModel", "Error fetching movies: ${e.message}")
                _movies.value = emptyList() // Ensure empty list on error
            }
        }
    }
}