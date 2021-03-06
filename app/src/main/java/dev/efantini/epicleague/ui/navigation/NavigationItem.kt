package dev.efantini.epicleague.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Send
import androidx.compose.ui.graphics.vector.ImageVector

const val ROOT_ROUTE = "root"

sealed class NavigationItem(
    var route: String,
    val fullRoute: String,
    var icon: ImageVector,
    var title: String
) {
    object Home : NavigationItem(
        "home", "home", Icons.Rounded.Home, "Home"
    )
    object Player : NavigationItem(
        "player",
        "player",
        Icons.Rounded.Person,
        "Player"
    )
    object PlayerList : NavigationItem(
        "player/playerlist",
        "player/playerlist",
        Icons.Rounded.Person,
        "PlayerList"
    )
    object PlayerDetail : NavigationItem(
        "player/playerdetail",
        "player/playerdetail/{playerId}",
        Icons.Rounded.Person,
        "PlayerDetail"
    )
    object Tournament : NavigationItem(
        "tournament",
        "tournament",
        Icons.Rounded.List,
        "Tournament"
    )
    object TournamentList : NavigationItem(
        "tournament/tournamentlist",
        "tournament/tournamentlist",
        Icons.Rounded.List,
        "TournamentList"
    )
    object TournamentDetail : NavigationItem(
        "tournament/tournamentdetail",
        "tournament/tournamentdetail/{tournamentId}",
        Icons.Rounded.List,
        "TournamentDetail"
    )
    object Sync : NavigationItem(
        "sync", "sync", Icons.Rounded.Send, "Sync"
    )
}
