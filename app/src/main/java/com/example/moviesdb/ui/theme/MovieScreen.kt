package com.example.moviesdb.ui.theme


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems


import com.example.moviesdb.viewmodel.MovieViewModel


@Composable
fun MovieScreen(viewModel: MovieViewModel, onMovieClick: (Int) -> Unit) {
    val lazyPagingItems = viewModel.pagedMovies.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        if (lazyPagingItems.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(lazyPagingItems.itemCount) { index ->
                    val movie = lazyPagingItems[index]

                    if (movie != null) {
                        MovieGridItem(
                            title = movie.title,
                            posterPath = movie.posterPath,
                            onClick = { onMovieClick(movie.id) })
                    }

                }
                if (lazyPagingItems.loadState.append is LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                                .padding(16.dp)

                        )
                    }
                }
            }

        }
        val errorState = lazyPagingItems.loadState.refresh as? LoadState.Error
            ?: lazyPagingItems.loadState.append as? LoadState.Error

        if (errorState != null) {
            Text(
                text = "Error: ${errorState.error.localizedMessage}",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }

    }


}


