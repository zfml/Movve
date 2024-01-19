package com.example.movve.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.movve.domain.model.Movie
import com.example.movve.presentation.home.MovieItem

@Composable
fun MoviesListContent(
    modifier: Modifier = Modifier,
    title: String = "",
    movies: LazyPagingItems<Movie>,
    onNavigateToMovieDetail: (id: String) -> Unit
) {
    if(movies.itemCount > 0) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(8.dp)
        )
    }

    LazyRow(
        modifier = Modifier,
        contentPadding = PaddingValues(
            start = 4.dp,
            end = 4.dp
        )
    ) {
        items(
            count = movies.itemCount,
        ) { index ->
            val movie = movies[index]
            movie?.let {
                MovieItem(
                    movie = movie,
                    onClickedDetail = {id ->
                        onNavigateToMovieDetail(id)
                    }
                )

            }
        }
    }
}
