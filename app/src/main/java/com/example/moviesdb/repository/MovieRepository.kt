package com.example.moviesdb.repository

import com.example.moviesdb.data.api.RetrofitClient
import com.example.moviesdb.data.model.MovieResponse
import com.example.moviesdb.BuildConfig


class MovieRepository {
    // This function simply passes the secure key to Retrofit
    suspend fun fetchPopularMovies(): MovieResponse {
        return RetrofitClient.api.getPopularMovies(BuildConfig.TMDB_API_KEY)
    }
}



