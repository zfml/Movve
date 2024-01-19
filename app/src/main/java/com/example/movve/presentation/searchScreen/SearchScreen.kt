package com.example.movve.presentation.searchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
    onNavigateToHomeScreen:() -> Unit,
    onNavigateToDetailScreen: (String) -> Unit
) {

    val searchQuery by searchViewModel.searchQuery

    val movies = searchViewModel.searchMovies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchBar(
                text = searchQuery ,
                onTextChange = {searchViewModel.updateSearchQuery(it)},
                onSearchClicked = {searchViewModel.searchMovies(searchQuery)},
                onClosedClicked = {onNavigateToHomeScreen()}
            )
        },
        content = { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(
                    items = movies,
                    key = {movie -> movie.id}
                ) {movie ->
                    movie?.let {
                        MovieSearchItem(
                            movie = movie,
                            onClickedDetail = {id ->
                                onNavigateToDetailScreen(id)                            }
                        )
                    }

                }
            }

        }

    )
}