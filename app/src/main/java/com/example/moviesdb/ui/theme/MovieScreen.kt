package com.example.moviesdb.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moviesdb.data.model.Movie
import com.example.moviesdb.viewmodel.MovieUiState
import com.example.moviesdb.viewmodel.MovieViewModel
import coil.compose.AsyncImage


@Composable
fun MovieScreen(viewModel: MovieViewModel = MovieViewModel(), onMovieClick: (Int) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is MovieUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))

            is MovieUiState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.movies) { Movie ->
                        MovieItem(movie = Movie, onClick = { movieId-> onMovieClick(movieId) })
                    }
                }
            }

            is MovieUiState.Error -> {
                Text(text = state.message, modifier = Modifier.align(Alignment.Center))
            }
        }

    }
}

@Composable
fun MovieItem(movie: Movie, onClick: (Int) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick(movie.id) }
        .padding(8.dp)) {
        val imageURL = "https://image.tmdb.org/t/p/w500${movie.posterPath}"

        AsyncImage(
            model = imageURL,
            contentDescription = "${movie.title} Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        )
        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movie.title, style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = movie.overview, style = MaterialTheme.typography.bodyMedium, maxLines = 3
            )
        }
    }
}