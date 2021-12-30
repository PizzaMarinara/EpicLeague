package dev.efantini.pauperarena.ui.elements

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.efantini.pauperarena.ui.navigation.BottomNavigationBar
import dev.efantini.pauperarena.ui.navigation.NavigationItem

@Composable
fun MainContent() {
    val navController = rememberNavController()

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
