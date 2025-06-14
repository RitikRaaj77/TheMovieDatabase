package com.example.themoviedatabase

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.themoviedatabase.userInterface.navigation.NavGraph
import com.example.themoviedatabase.userInterface.theme.TheMovieDatabaseTheme
import com.example.themoviedatabase.userInterface.viewmodel.MovieDetailViewModel
import com.example.themoviedatabase.userInterface.viewmodel.MovieListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            TheMovieDatabaseTheme {
                App()
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun App() {
    val navController = rememberNavController()
    val movieListViewModel = MovieListViewModel()
    val movieDetailViewModel = MovieDetailViewModel()
    NavGraph(navController, movieListViewModel, movieDetailViewModel)
}