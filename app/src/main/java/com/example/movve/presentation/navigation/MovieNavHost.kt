package com.example.movve.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movve.presentation.home.HomeScreen
import com.example.movve.presentation.movieDetail.MovieDetail
import com.example.movve.presentation.searchScreen.SearchScreen
import com.example.movve.presentation.util.Constants.MOVIE_DETAIL_SCREEN
import com.example.movve.presentation.util.Constants.MOVIE_DETAIL_SCREEN_ARG
import com.example.movve.presentation.util.Constants.MOVIE_ID

@Composable
fun MovieNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable(
            route = "Home"
        ) {
            HomeScreen(
                onNavigateToMovieDetail = {
                    navController.navigate("${MOVIE_DETAIL_SCREEN}/$it")
                },
                onNavigateToSearchScreen = {
                    navController.navigate("SearchScreen")
                }

            )
        }

        composable(
            route = "SearchScreen"
        ) {
            SearchScreen(
                onNavigateToHomeScreen = {
                    navController.popBackStack()
                },
                onNavigateToDetailScreen = {
                    navController.navigate("${MOVIE_DETAIL_SCREEN}/$it")
                }

            )
        }

        composable(
            route = MOVIE_DETAIL_SCREEN_ARG,
            arguments = listOf(
                navArgument(name = MOVIE_ID){
                    type = NavType.StringType
                }
            )
        ) {
            MovieDetail()
        }

    }
}
