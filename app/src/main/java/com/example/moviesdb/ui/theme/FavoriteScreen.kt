package com.example.moviesdb.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.room.util.TableInfo
import coil.compose.AsyncImage
import com.example.moviesdb.viewmodel.FavoritesViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoritesViewModel = hiltViewModel(), onMovieClick: (Int) -> Unit
) {
    val favorites by viewModel.favoriteMovies.collectAsState()

    if (favorites.isEmpty()) {
        Text("No favorites yet. Go add some!", modifier = Modifier.padding(16.dp))
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.systemBarsPadding()
        ) {
            items(favorites) { movie ->
                MovieGridItem(
                    title = movie.title,
                    posterPath = movie.posterPath,
                    onClick = {onMovieClick(movie.id)}
                )
            }
        }
    }
}