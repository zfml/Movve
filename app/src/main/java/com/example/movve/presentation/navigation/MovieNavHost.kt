package com.example.movve.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movve.presentation.home.HomeScreen

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
            HomeScreen()
        }
    }
}
