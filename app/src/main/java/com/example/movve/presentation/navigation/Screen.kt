package com.example.movve.presentation.navigation

import com.example.movve.presentation.util.Constants.MOVIE_ID_KEY

sealed class Screen(val route: String){
    object Home: Screen(route = "home_route")
    object SearchScreen: Screen(route = "search_screen_route")
    object DetailScreen: Screen(route = "detail_route?$MOVIE_ID_KEY={$MOVIE_ID_KEY}") {
        fun passById(movieId: String) =  "detail_route?$MOVIE_ID_KEY= $movieId"
    }

}