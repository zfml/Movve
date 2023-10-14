package com.example.movve.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.movve.R
import com.example.movve.data.remote.MovieDto
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PopularMovieViewModel = hiltViewModel(),
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
                IconButton(onClick = { /*TODO*/ }) {
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

                    PopularMoviesListContent(popularMovies = nowPlayingMovies)

                    Text(
                        text = "Now Playing Movies",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    PopularMoviesListContent(popularMovies = popularMovies)


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
fun PopularMoviesListContent(
    popularMovies: LazyPagingItems<MovieDto>
) {
    LazyRow(
        modifier = Modifier,
        contentPadding = PaddingValues(
            start = 4.dp,
            end = 4.dp
        )
    ) {
        items(
            count = popularMovies.itemCount,
        ) { index ->
            val movie = popularMovies[index]
            movie?.let {
                MovieItem(movie = movie)

            }
        }
    }
}

@Composable
fun MovieItem(
    movie: MovieDto,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .padding(4.dp)
            .width(150.dp)
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
//                        model ="http://image.tmdb.org/t/p/w500/Af4bXE63pVsb2FtbW8uYIyPBadD.jpg" ,
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