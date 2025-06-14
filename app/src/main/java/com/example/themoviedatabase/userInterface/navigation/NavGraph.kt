package com.example.themoviedatabase.userInterface.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.themoviedatabase.userInterface.screens.MovieDetailScreen
import com.example.themoviedatabase.userInterface.screens.MovieListScreen
import com.example.themoviedatabase.userInterface.screens.SplashScreen
import com.example.themoviedatabase.userInterface.viewmodel.MovieDetailViewModel
import com.example.themoviedatabase.userInterface.viewmodel.MovieListViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    movieListViewModel: MovieListViewModel,
    movieDetailViewModel: MovieDetailViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController, viewModel = movieListViewModel)
        }
        composable(Screen.MovieList.route) {
            MovieListScreen(
                navController = navController,
                viewModel = movieListViewModel,
                onMovieClick = { movie ->
                    movieDetailViewModel.selectMovie(movie)
                    navController.navigate(Screen.MovieDetail.route)
                }
            )
        }
        composable(Screen.MovieDetail.route) {
            MovieDetailScreen(
                navController = navController,
                viewModel = movieDetailViewModel
            )
        }
    }
}