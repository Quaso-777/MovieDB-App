package com.example.moviesdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val movies: List<Movie>) : SearchUiState
    data class Error(val message: String) : SearchUiState
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            _searchQuery
                .debounce { 500L }
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isBlank()){
                        _uiState.value = SearchUiState.Idle
                        return@collectLatest
                    }

                    _uiState.value = SearchUiState.Loading
                    try {
                        val results = repository.searchMovies(query)
                        _uiState.value = SearchUiState.Success(results)
                    }catch (e: Exception){
                        _uiState.value = SearchUiState.Error(e.message ?: "Unknown error")
                    }
                }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery

    }
}