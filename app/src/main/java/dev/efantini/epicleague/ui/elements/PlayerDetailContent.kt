package dev.efantini.epicleague.ui.elements

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.efantini.epicleague.ui.viewmodels.PlayerDetailViewModel

@Composable
fun PlayerDetailContent(
    playerDetailViewModel: PlayerDetailViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val nome = playerDetailViewModel.playerDetailContentUiState.player?.fullName ?: ""
    Text(text = nome)
}
