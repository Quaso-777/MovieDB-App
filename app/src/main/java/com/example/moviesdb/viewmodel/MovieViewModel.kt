package com.example.moviesdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdb.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel: ViewModel() {
    val repository = MovieRepository()

    fun loadMovies(){
        viewModelScope.launch {
            try {
                val response = repository.fetchPopularMovies()
                val moviesList = response.results
                println("Success, Fetched ${moviesList.size} movies")
            }
            catch (e: Exception){
                println("Uh oh, something broke: ${e.message}")
            }
        }
    }

}