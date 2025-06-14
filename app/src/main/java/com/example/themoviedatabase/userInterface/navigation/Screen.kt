package com.example.themoviedatabase.userInterface.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object MovieList : Screen("movie_list")
    object MovieDetail : Screen("movie_detail")
}