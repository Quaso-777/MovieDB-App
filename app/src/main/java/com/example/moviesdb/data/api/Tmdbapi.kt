package com.example.moviesdb.data.api

import com.example.moviesdb.BuildConfig
import com.example.moviesdb.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val runtime: Int
)


interface Tmdbapi {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieDetails


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY, @Query("page") page: Int = 1
    ): MovieResponse
}
