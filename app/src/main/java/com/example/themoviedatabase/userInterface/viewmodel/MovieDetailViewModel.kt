package com.example.themoviedatabase.userInterface.viewmodel

import androidx.lifecycle.ViewModel
import com.example.themoviedatabase.data.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieDetailViewModel : ViewModel() {
    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }
}