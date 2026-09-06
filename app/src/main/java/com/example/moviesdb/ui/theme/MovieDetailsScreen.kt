package com.example.moviesdb.ui.theme
import com.example.moviesdb.viewmodel.MovieDetailsUiState
import com.example.moviesdb.viewmodel.MovieDetailsViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage


@Composable
fun MovieDetailsScreen(
    onNavigateBack: () -> Unit,
    viewModel: MovieDetailsViewModel = viewModel() // The ViewModel handles the ID internally!
) {
    // 1. Observe the state in a lifecycle-aware way
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is MovieDetailsUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is MovieDetailsUiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onNavigateBack) {
                        Text("Go Back")
                    }
                }
            }

            is MovieDetailsUiState.Success -> {
                // 2. Render the rich data from the new endpoint
                val details = state.details

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Back button at the top
                    Button(onClick = onNavigateBack) {
                        Text("Back to List")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${details.poster_path}",
                        contentDescription = "${details.title} Poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = details.title, style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    // We can now show data we didn't have before, like runtime!
                    Text(
                        text = "Release Date: ${details.release_date} • Runtime: ${details.runtime} mins",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = details.overview, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}