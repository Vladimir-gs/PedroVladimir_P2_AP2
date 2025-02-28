package edu.ucne.pedrovladimir_p2_ap2.presentation.navigation

import DepositoList
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.pedrovladimir_p2_ap2.presentation.depositos.DepositoEdit
import edu.ucne.pedrovladimir_p2_ap2.presentation.depositos.DepositoScreen
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

        composable<Screen.DepositoLis> {
            DepositoList(
                onCreate = { navController.navigate(Screen.Deposito(0)) },
                onEdit = {navController.navigate(Screen.DepositoEdit(it)) },
                onBack = { navController.navigateUp() },
                onDelete = {}
            )
        }

        composable<Screen.Deposito> {
            DepositoScreen(
                goBack = { navController.navigateUp() }
            )
        }

        composable<Screen.DepositoEdit> {backStackEntry ->
            val args = backStackEntry.toRoute<Screen.DepositoEdit>()
            DepositoEdit(
                goBack = { navController.navigateUp() },
                idDeposito = args.DepositoId
            )
        }
    }
}