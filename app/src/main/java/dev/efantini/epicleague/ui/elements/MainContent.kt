package dev.efantini.epicleague.ui.elements

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.efantini.epicleague.ui.navigation.BottomNavigationBar
import dev.efantini.epicleague.ui.navigation.EpicLeagueNavHost
import dev.efantini.epicleague.ui.theme.EpicLeagueTheme

@ExperimentalPagerApi
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
            EpicLeagueNavHost(navController = navController)
        }
    }
}
