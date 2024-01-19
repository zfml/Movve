package com.example.movve.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movve.presentation.home.HomeScreen
import com.example.movve.presentation.movieDetail.MovieDetailScreen
import com.example.movve.presentation.searchScreen.SearchScreen
import com.example.movve.presentation.util.Constants.MOVIE_ID_KEY

@Composable
fun SetUpNavGraph(
    navController : NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeRoute(
            navigateToDetailScreen = {
                navController.navigate(Screen.DetailScreen.passById(it))
            },
            navigateToSearchScreen = {
                navController.navigate(Screen.SearchScreen.route)
            }
        )

        searchScreenRoute(
            navigateToDetailScreen = {
                navController.navigate(Screen.DetailScreen.passById(it))
            },
            navigateToHomeScreen = {
                navController.popBackStack()
            }
        )

        movieDetailRoute(
            navigateToBackScreen = {
                navController.popBackStack()
            }
        )

    }
}
fun NavGraphBuilder.homeRoute(
    navigateToDetailScreen: (String) -> Unit,
    navigateToSearchScreen: () -> Unit
) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            onNavigateToMovieDetail = navigateToDetailScreen,
            onNavigateToSearchScreen = navigateToSearchScreen
        )
    }
}

fun NavGraphBuilder.searchScreenRoute(
    navigateToHomeScreen: () -> Unit,
    navigateToDetailScreen: (String) -> Unit
){
    composable(route = Screen.SearchScreen.route) {
         SearchScreen(
             onNavigateToHomeScreen = navigateToHomeScreen,
             onNavigateToDetailScreen = navigateToDetailScreen
         )
    }
}

fun NavGraphBuilder.movieDetailRoute(
    navigateToBackScreen: () -> Unit
) {
    composable(
        route = Screen.DetailScreen.route,
        arguments = listOf(
            navArgument(name = MOVIE_ID_KEY) {
                type = NavType.StringType
            }
        )
    ) {
        MovieDetailScreen(
            onNavigateToHomeScreen = navigateToBackScreen
        )
    }
}