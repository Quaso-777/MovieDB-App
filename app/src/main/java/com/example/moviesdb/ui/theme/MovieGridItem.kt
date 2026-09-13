package com.example.moviesdb.ui.theme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun MovieGridItem(
    title: String,
    posterPath: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500$posterPath",
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}