package com.example.moviesdb.viewmodel

import com.example.moviesdb.data.api.MovieDetails
import com.example.moviesdb.repository.MovieRepository
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface MovieDetailsUiState {
    data object Loading : MovieDetailsUiState
    data class Success(val details: MovieDetails) : MovieDetailsUiState
    data class Error(val message: String) : MovieDetailsUiState
}




class MovieDetailsViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val repository: MovieRepository = MovieRepository()
    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()

    init {
        val movieId: Int? = savedStateHandle.get<Int>("movieId")

        if (movieId != null) {
            loadMovieDetails(movieId)
        } else {
            _uiState.value = MovieDetailsUiState.Error("Movie ID is missing")
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = MovieDetailsUiState.Loading
            try {
                val details = repository.fetchMovieDetails(movieId)
                _uiState.value = MovieDetailsUiState.Success(details)
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching movie details", e)
                _uiState.value = MovieDetailsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        private const val TAG = "MovieDetailsViewModel"
    }
}