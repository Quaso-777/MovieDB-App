package com.example.moviesdb.ui.theme


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.example.moviesdb.viewmodel.MovieUiState
import com.example.moviesdb.viewmodel.MovieViewModel


@Composable
fun MovieScreen(viewModel: MovieViewModel, onMovieClick: (Int) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is MovieUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))

            is MovieUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()

                ) {
                    items(state.movies) { movie ->
                        MovieGridItem(
                            title = movie.title,
                            posterPath = movie.posterPath,
                            onClick = { onMovieClick(movie.id) })
                    }
                }
            }

            is MovieUiState.Error -> {
                Text(text = state.message, modifier = Modifier.align(Alignment.Center))
            }
        }

    }
}

