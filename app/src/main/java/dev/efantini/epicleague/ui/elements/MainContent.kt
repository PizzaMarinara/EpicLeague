package dev.efantini.epicleague.ui.elements

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.efantini.epicleague.ui.navigation.BottomNavigationBar
import dev.efantini.epicleague.ui.navigation.NavigationItem
import dev.efantini.epicleague.ui.theme.EpicLeagueTheme

@Composable
fun MainContent() {
    val navController = rememberNavController()

    EpicLeagueTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    navController = navController
                )
            }
        ) {
            NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
                composable(NavigationItem.Home.route) {
                }
                composable(NavigationItem.PlayersList.route) {
                    PlayersListContent()
                }
                composable(NavigationItem.Tournaments.route) {
                }
                composable(NavigationItem.Sync.route) {
                }
            }
        }
    }
}
