package edu.ucne.pedrovladimir_p2_ap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.pedrovladimir_p2_ap2.presentation.home.Home

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        //Home
        composable<Screen.Home> {
            Home(
                navController = navController
            )
        }
    }
}