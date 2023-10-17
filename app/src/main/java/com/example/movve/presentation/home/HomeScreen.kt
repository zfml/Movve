package com.example.movve.presentation.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movve.data.remote.MovieDto
import dagger.hilt.android.qualifiers.ApplicationContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToMovieDetail: (id: String) -> Unit,
    onNavigateToSearchScreen: () -> Unit
) {

    val popularMovies = viewModel.getPopularMovies().collectAsLazyPagingItems()
    val nowPlayingMovies = viewModel.getNowPlayingMovies().collectAsLazyPagingItems()

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                Text(text = "Movve")
            },
            actions = {
                IconButton(onClick = { onNavigateToSearchScreen() }) {
                    Image(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            }
            )
        },
        content = {innerPadding ->

            if(popularMovies.loadState.refresh is LoadState.Loading ||
                nowPlayingMovies.loadState.refresh is LoadState.Loading) {
                LoadingScreen()
            } else{
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {

                    Text(
                        text = "Popular Movies",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(8.dp)
                    )

                    MoviesListContent(
                        movies = nowPlayingMovies,
                        onNavigateToMovieDetail = onNavigateToMovieDetail
                    )

                    Text(
                        text = "Now Playing Movies",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    MoviesListContent(
                        movies = popularMovies,
                        onNavigateToMovieDetail = onNavigateToMovieDetail
                    )


                }
            }


        }
    )



}

@Composable
fun LoadingScreen(
    modifier:Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MoviesListContent(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<MovieDto>,
    onNavigateToMovieDetail: (id: String) -> Unit
) {
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

@Composable
fun MovieItem(
    movie: MovieDto,
    onClickedDetail: (id: String) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .padding(4.dp)
            .width(150.dp)
            .clickable {
                       onClickedDetail(movie.id)
            }
        ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(194.dp)
                ,
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/original${movie.poster_path}")
                    .build()
                ,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop
            )

//            Divider(
//                modifier = Modifier.height(8.dp)
//            )

            Text(
                text = movie.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                fontSize = 16.sp,
            )

            Text(
                text = movie.release_date,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}