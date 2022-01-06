package dev.efantini.epicleague.ui.elements

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import dev.efantini.epicleague.ui.theme.KarlaFontFamily
import dev.efantini.epicleague.ui.viewmodels.PlayerDetailViewModel

@Composable
fun PlayerDetailContent(
    playerDetailViewModel: PlayerDetailViewModel = hiltViewModel()
) {
    val nome = playerDetailViewModel.playerDetailContentUiState.player?.fullName ?: ""
    Text(text = nome, fontFamily = KarlaFontFamily)
}
