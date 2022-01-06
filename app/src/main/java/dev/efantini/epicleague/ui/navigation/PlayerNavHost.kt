package dev.efantini.epicleague.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import dev.efantini.epicleague.ui.elements.PlayerDetailContent
import dev.efantini.epicleague.ui.elements.PlayersListContent

fun NavGraphBuilder.playerNavHost(navController: NavController) {
    navigation(
        startDestination = NavigationItem.PlayerList.fullRoute,
        route = NavigationItem.Player.route
    ) {
        composable(NavigationItem.PlayerList.fullRoute) {
            PlayersListContent(navController = navController)
        }
        composable(
            NavigationItem.PlayerDetail.fullRoute,
            arguments = listOf(navArgument("playerId") { type = NavType.LongType })
        ) {
            PlayerDetailContent()
        }
    }
}
