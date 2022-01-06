package dev.efantini.epicleague.ui.states

import dev.efantini.epicleague.data.models.Player

data class DeckListUiState(
    val userFilter: Player? = null,
    val deckItems: List<DeckItemUiState> = listOf()
)
