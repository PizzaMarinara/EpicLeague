package dev.efantini.pauperarena.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Send
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Home : NavigationItem("home", Icons.Rounded.Home, "Home")
    object PlayersList : NavigationItem("playerslist", Icons.Rounded.Person, "PlayersList")
    object Tournaments : NavigationItem("tournaments", Icons.Rounded.List, "Tournaments")
    object Sync : NavigationItem("sync", Icons.Rounded.Send, "Sync")
}
