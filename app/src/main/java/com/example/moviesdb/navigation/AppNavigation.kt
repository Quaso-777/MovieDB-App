package com.example.moviesdb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesdb.ui.theme.MovieDetailsScreen
import com.example.moviesdb.ui.theme.MovieScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel 
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.viewmodel.MovieDetailsViewModel
import com.example.moviesdb.viewmodel.MovieViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MovieList.route) {

        //MOVIE LIST SCREEN
        composable(route = Screen.MovieList.route) {
            val listViewModel : MovieViewModel = hiltViewModel()
            MovieScreen(
                viewModel =  listViewModel,
                onMovieClick = { movieId ->
                    navController.navigate(Screen.MovieDetails.createRoute(movieId))
                })
        }

        //MOVIE DETAILS SCREEN
        composable(
            route = Screen.MovieDetails.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val detailsViewModel : MovieDetailsViewModel = hiltViewModel()
            // we dont need id param here
            // as the SavedStateHandle inside MovieDetailsViewModel does it automatically
            MovieDetailsScreen(
                onNavigateBack = { navController.popBackStack() },
                viewModel = detailsViewModel,
            )
        }
    }
}