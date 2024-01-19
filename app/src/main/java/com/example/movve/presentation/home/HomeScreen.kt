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
import com.example.movve.domain.model.Movie
import com.example.movve.presentation.component.MoviesListContent
import dagger.hilt.android.qualifiers.ApplicationContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToMovieDetail: (String) -> Unit,
    onNavigateToSearchScreen: () -> Unit
) {

    val popularMovies = viewModel.getPopularMovies().collectAsLazyPagingItems()
    val nowPlayingMovies = viewModel.getNowPlayingMovies().collectAsLazyPagingItems()

    Scaffold (
        topBar = {
              MovieTopBar(
                  title = "Movve",
                  onNavigateToSearchScreen = onNavigateToSearchScreen
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
                    MoviesListContent(
                        title = "Popular Movies",
                        movies = popularMovies,
                        onNavigateToMovieDetail = onNavigateToMovieDetail
                    )
                    MoviesListContent(
                        title = "Now Playing Movies",
                        movies = nowPlayingMovies,
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
fun MovieItem(
    movie: Movie,
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
                    .data("https://image.tmdb.org/t/p/original${movie.posterPath}")
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
                text = movie.releaseDate,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}