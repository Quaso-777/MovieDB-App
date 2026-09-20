package com.example.moviesdb.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesdb.data.model.MovieResponse
import com.example.moviesdb.BuildConfig
import com.example.moviesdb.data.api.MovieDetails
import com.example.moviesdb.data.api.Tmdbapi
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.database.MovieDao
import com.example.moviesdb.database.MovieDao.*
import com.example.moviesdb.database.AppDatabase
import com.example.moviesdb.database.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.collections.emptyList


class MovieRepository @Inject constructor(
    private val movieDao: MovieDao, private val api: Tmdbapi
) {

    //RETROFIT/TMDB (REMOTE DATA)
    fun fetchPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, //TMBD returns 20 items per page
                enablePlaceholders = false
            ), pagingSourceFactory = { MoviePagingSource(api) }).flow
    }

    suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        return api.getMovieDetails(movieId)
    }


    // ROOM DATABASE (LOCAL DATA)

    val favoriteMovies: Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    suspend fun toggleFavorite(movie: MovieEntity, isFavorite: Boolean) {
        if (isFavorite) {
            movieDao.deleteFavorite(movie.id)
        } else {
            movieDao.insertFavorite(movie)
        }
    }

    suspend fun searchMovies(query: String): List<Movie> {
        return if (query.isNotBlank()) {
            api.searchMovies(query).results
        } else {
            emptyList<Movie>()
        }
    }
}



