package dev.efantini.epicleague.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.efantini.epicleague.ui.elements.TournamentDetailContent
import dev.efantini.epicleague.ui.elements.TournamentListContent

@ExperimentalPagerApi
fun NavGraphBuilder.tournamentNavHost(navController: NavController) {
    navigation(
        startDestination = NavigationItem.TournamentList.fullRoute,
        route = NavigationItem.Tournament.route
    ) {
        composable(NavigationItem.TournamentList.fullRoute) {
            TournamentListContent(navController = navController)
        }
        composable(
            NavigationItem.TournamentDetail.fullRoute,
            arguments = listOf(navArgument("tournamentId") { type = NavType.LongType })
        ) {
            TournamentDetailContent()
        }
    }
}
