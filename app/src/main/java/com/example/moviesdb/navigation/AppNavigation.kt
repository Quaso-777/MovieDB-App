package com.example.moviesdb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesdb.ui.theme.MovieDetailsScreen
import com.example.moviesdb.ui.theme.MovieScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MovieList.route) {
        composable(route = Screen.MovieList.route) {
            MovieScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.MovieDetails.createRoute(movieId))
                })
        }
        composable(
            route = Screen.MovieDetails.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            // we dont need id param here
            // as the SavedStateHandle inside MovieDetailsViewModel does it automatically
            MovieDetailsScreen( onNavigateBack = { navController.popBackStack() })
        }
    }
}