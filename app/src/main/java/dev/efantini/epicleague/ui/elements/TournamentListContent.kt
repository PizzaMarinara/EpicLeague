package dev.efantini.epicleague.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dev.efantini.epicleague.ui.navigation.NavigationItem
import dev.efantini.epicleague.ui.viewmodels.TournamentListViewModel

@ExperimentalPagerApi
@Composable
fun TournamentListContent(
    tournamentListViewModel: TournamentListViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val state = tournamentListViewModel.tournamentListContentUiState

    val items = listOf(
        TournamentListTabItem.Ongoing.also { it.list = state.ongoingTournaments },
        TournamentListTabItem.Completed.also { it.list = state.completedTournaments }
    )
    val pagerState = rememberPagerState()

    Scaffold(
        floatingActionButton = {
            AddFloatingActionButton(onClick = {
                navController.navigate(
                    NavigationItem.TournamentDetail.route +
                        "/0"
                )
            })
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Column {
                TournamentListTabs(tabs = items, pagerState = pagerState)
                TournamentTabsContent(
                    tabs = items,
                    pagerState = pagerState,
                    navController = navController
                )
            }
        }
    }
}
