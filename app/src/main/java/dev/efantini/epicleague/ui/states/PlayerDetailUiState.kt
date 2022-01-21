package dev.efantini.epicleague.ui.states

import dev.efantini.epicleague.data.models.Player

data class PlayerDetailUiState(
    val player: Player = Player(),
    val decks: List<DeckItemUiState> = listOf()
)
