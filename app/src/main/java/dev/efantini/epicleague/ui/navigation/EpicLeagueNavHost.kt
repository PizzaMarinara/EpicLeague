package dev.efantini.epicleague.ui.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.efantini.epicleague.ui.elements.PlayerDetailContent
import dev.efantini.epicleague.ui.elements.PlayersListContent

@Composable
fun EpicLeagueNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavigationItem.Home.fullRoute) {
            Text(text = "Home")
        }
        composable(NavigationItem.PlayersList.fullRoute) {
            PlayersListContent(navController = navController)
        }
        composable(
            NavigationItem.PlayerDetail.fullRoute,
            arguments = listOf(navArgument("playerId") { type = NavType.LongType })
        ) {
            PlayerDetailContent(
                navController = navController
            )
        }
        composable(NavigationItem.Tournaments.fullRoute) {
            Text(text = "Tournaments")
        }
        composable(NavigationItem.Sync.fullRoute) {
            Text(text = "Sync")
        }
    }
}
