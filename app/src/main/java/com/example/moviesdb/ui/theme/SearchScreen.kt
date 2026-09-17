package com.example.moviesdb.ui.theme

import android.graphics.Paint
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.moviesdb.viewmodel.SearchUiState
import com.example.moviesdb.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
) {
    val query by viewModel.searchQuery.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onQueryChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search movies...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))

        Box(Modifier.fillMaxSize()) {
            when (val state = uiState) {
                is SearchUiState.Idle -> {
                    Text(
                        text = "Search Movies",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                is SearchUiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                is SearchUiState.Success -> {
                    if (state.movies.isEmpty()) {
                        Text(
                            text = "No movies found",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3), // Matches your 3-column design
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.movies) { movie ->
                                MovieGridItem(
                                    title = movie.title,
                                    posterPath = movie.posterPath,
                                    onClick = { onMovieClick(movie.id) }
                                )
                            }
                        }
                    }
                }
                is SearchUiState.Error ->{
                    Text(text = "Error : ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}