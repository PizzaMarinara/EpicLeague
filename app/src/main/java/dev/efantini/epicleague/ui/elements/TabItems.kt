package dev.efantini.epicleague.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.LeadingIconTab
import androidx.compose.material.Surface
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import dev.efantini.epicleague.ui.navigation.NavigationItem
import dev.efantini.epicleague.ui.states.TournamentItemUiState
import dev.efantini.epicleague.ui.theme.DEFAULT_SIDE_PADDING
import dev.efantini.epicleague.ui.theme.DEFAULT_VERTICAL_SPACING
import dev.efantini.epicleague.ui.theme.LocalSpacing
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun TournamentListTabs(tabs: List<TournamentListTabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    // OR ScrollableTabRow()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            // OR Tab()
            LeadingIconTab(
                icon = { Icon(tab.icon, contentDescription = "") },
                text = { Text(tab.title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TournamentTabsContent(
    tabs: List<TournamentListTabItem>,
    pagerState: PagerState,
    navController: NavController
) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        Surface(
            modifier = Modifier
                .padding(DEFAULT_SIDE_PADDING)
        ) {
            Column {
                Spacer(modifier = Modifier.height(DEFAULT_VERTICAL_SPACING))
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.default),
                ) {
                    items(tabs[page].list ?: listOf()) {
                        Box(
                            Modifier.clickable(onClick = {
                                navController.navigate(
                                    NavigationItem.TournamentDetail.route +
                                        "/" + it.tournament.id.toString()
                                )
                            })
                        ) {
                            TournamentCard(it)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(DEFAULT_VERTICAL_SPACING))
                    }
                }
            }
        }
    }
}

sealed class TournamentListTabItem(
    var icon: ImageVector,
    var title: String,
    var list: List<TournamentItemUiState>?
) {
    object Ongoing : TournamentListTabItem(
        Icons.Rounded.List,
        "Ongoing", null
    )
    object Completed : TournamentListTabItem(
        Icons.Rounded.Check,
        "Completed", null
    )
}
