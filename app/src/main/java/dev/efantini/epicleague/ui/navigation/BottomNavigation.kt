package dev.efantini.epicleague.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.efantini.epicleague.ui.theme.BOTTOMNAVBAR_HEIGHT

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Player,
        NavigationItem.Tournament,
        NavigationItem.Sync
    )
    BottomNavigation(
        modifier = Modifier.height(BOTTOMNAVBAR_HEIGHT)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        maxLines = 1
                    )
                },
                alwaysShowLabel = true,
                selected = currentRoute?.hierarchy?.any { it.route == item.fullRoute } == true,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        // restoreState = true
                    }
                }
            )
        }
    }
}
