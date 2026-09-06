package com.example.moviesdb.repository

import com.example.moviesdb.data.api.RetrofitClient
import com.example.moviesdb.data.model.MovieResponse
import com.example.moviesdb.BuildConfig
import com.example.moviesdb.data.api.MovieDetails


class MovieRepository {
    // This function simply passes the secure key to Retrofit
    suspend fun fetchPopularMovies(): MovieResponse {
        return RetrofitClient.api.getPopularMovies(BuildConfig.TMDB_API_KEY)
    }

    suspend fun fetchMovieDetails(movieId: Int): MovieDetails{
        return RetrofitClient.api.getMovieDetails(movieId)
    }
}



