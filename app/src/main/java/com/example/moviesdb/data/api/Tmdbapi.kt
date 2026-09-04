package com.example.moviesdb.data.api

import com.example.moviesdb.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Tmdbapi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): MovieResponse
}
