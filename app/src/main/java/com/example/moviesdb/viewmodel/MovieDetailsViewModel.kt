package com.example.moviesdb.viewmodel

import com.example.moviesdb.data.api.MovieDetails
import com.example.moviesdb.repository.MovieRepository
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdb.database.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MovieDetailsUiState {
    data object Loading : MovieDetailsUiState
    data class Success(val details: MovieDetails) : MovieDetailsUiState
    data class Error(val message: String) : MovieDetailsUiState
}


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val movieId: Int? = savedStateHandle.get<Int>("movieId")
    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()
    val isFavorite: StateFlow<Boolean> = repository.favoriteMovies
        .map { favoriteList ->
            if (movieId == null) false else favoriteList.any { it.id == movieId }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {


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

    fun onToggleFavoriteClick(movieDetails: MovieDetails) {
        viewModelScope.launch {
            val currentlyFavorite = isFavorite.value
            val entity = MovieEntity(
                id = movieDetails.id,
                title = movieDetails.title,
                posterPath = movieDetails.poster_path,
                overview = movieDetails.overview
            )
            repository.toggleFavorite(entity, currentlyFavorite)
        }
    }

    companion object {
        private const val TAG = "MovieDetailsViewModel"
    }
}