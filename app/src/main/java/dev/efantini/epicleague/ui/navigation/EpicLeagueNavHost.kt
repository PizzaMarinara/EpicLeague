package dev.efantini.epicleague.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.efantini.epicleague.ui.elements.ComingSoon

@Composable
fun EpicLeagueNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {
    NavHost(
        navController = navController, startDestination = startDestination, route = ROOT_ROUTE
    ) {
        composable(NavigationItem.Home.fullRoute) {
            ComingSoon()
        }

        playerNavHost(navController = navController)

        tournamentNavHost(navController = navController)

        composable(NavigationItem.Sync.fullRoute) {
            ComingSoon()
        }
    }
}
