package com.example.themoviedatabase.userInterface.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.themoviedatabase.data.model.Movie
import com.example.themoviedatabase.userInterface.viewmodel.MovieListViewModel

@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel,
    onMovieClick: (Movie) -> Unit
) {
    val movies = viewModel.movies.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(movies) { movie ->
            MovieItem(movie = movie, onClick = { onMovieClick(movie) })
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w154${movie.poster_path}",
            contentDescription = movie.title,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 16.dp)
        )
        Column {
            Text(text = movie.title, fontSize = 18.sp)
            Text(text = movie.release_date, fontSize = 14.sp)
        }
    }
}