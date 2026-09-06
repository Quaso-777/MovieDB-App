package com.example.moviesdb.navigation

sealed class Screen(val route: String) {
    data object MovieList: Screen("movie_list")
    data object MovieDetails : Screen("movie_details/{movieId}"){
        fun createRoute(movieId:Int): String{
            return "movie_details/$movieId"

        }
    }
}