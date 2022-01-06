package dev.efantini.epicleague.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Send
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    var route: String,
    val fullRoute: String,
    var icon: ImageVector,
    var title: String
) {
    object Home : NavigationItem(
        "home", "home", Icons.Rounded.Home, "Home"
    )
    object PlayersList : NavigationItem(
        "playerslist", "playerslist", Icons.Rounded.Person, "PlayersList"
    )
    object PlayerDetail : NavigationItem(
        "playerdetail", "playerdetail/{playerId}", Icons.Rounded.Person, "PlayerDetail"
    )
    object Tournaments : NavigationItem(
        "tournaments", "tournaments", Icons.Rounded.List, "Tournaments"
    )
    object Sync : NavigationItem(
        "sync", "sync", Icons.Rounded.Send, "Sync"
    )
}