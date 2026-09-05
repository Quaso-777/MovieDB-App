package com.example.moviesdb.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface MovieUiState {
    data object Loading : MovieUiState
    data class Success(val movies: List<Movie>) : MovieUiState
    data class Error(val message: String) : MovieUiState
}

class MovieViewModel(private val repository: MovieRepository = MovieRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState : StateFlow<MovieUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies(){
        viewModelScope.launch {
            _uiState.value = MovieUiState.Loading
            try {
                val response = repository.fetchPopularMovies()
                _uiState.value = MovieUiState.Success(response.results)
            }catch (e: Exception){
                Log.e(TAG,"Error fetching Movies", e)
                _uiState.value = MovieUiState.Error(e.message?:"Unknown error")
            }
        }
    }
    companion object{
        private const val TAG = "MovieViewModel"
    }
}